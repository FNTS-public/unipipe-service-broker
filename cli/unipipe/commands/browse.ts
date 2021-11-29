import { colors, Command, Select, prompt, Input } from "../deps.ts";
import { mapInstances } from "./helpers.ts";
import { show } from "./show.ts";
import { STATUSES, update } from "./update.ts";

export function registerBrowseCmd(program: Command) {
  program
    .command("browse <repo>")
    .description("Interactively browse and manipulate a UniPipe OSB git repo.")
    .action(async (opts: Record<never, never>, repo: string) => {
      await browse(repo);
    });
}

async function browse(osbRepoPath: string) {
  // deno-lint-ignore require-await
  const instanceOptions = await mapInstances(osbRepoPath, async (x) => {
    const i = x.instance;
    const plan = i.serviceDefinition.plans.filter((x) => x.id === i.planId)[0];

    const name = [
      colors.dim(i.serviceInstanceId),
      colors.green(i.serviceDefinition.name),
      colors.brightGreen(plan.name),
      colors.blue(x.status?.status || "new"),
      colors.red(i.deleted ? "deleted" : ""),
    ].join(" ");

    return { name, value: i.serviceInstanceId };
  });

  await prompt([
    {
      name: "instances",
      type: Select,
      message: "Pick a service instance",
      options: instanceOptions,
    },
    {
      name: "cmd",
      message: "What do you want to do with this instance?",
      type: Select,
      options: ["show", "update", "↑ instances"],
      after: async ({ instances, cmd }, next) => {
        switch (cmd!!) {
          case "show":
            await showInstance(osbRepoPath, instances!!);
            next("cmd"); // loop
            break;
          case "update": {
            await updateInstance(osbRepoPath, instances!!);
            next("cmd"); // loop
            break;
          }
          case "↑ instances":
            next("instances"); // back up
            break;
          default:
            break;
        }
      },
    },
  ]);
}

async function showInstance(osbRepoPath: string, instanceId: string) {
  await show(osbRepoPath, {
    instanceId: instanceId,
    outputFormat: "yaml",
    pretty: true,
  });
}

async function updateInstance(osbRepoPath: string, instanceId: any) {
  const { status, description } = await prompt([
    {
      name: "status",
      message: "What's the new status?",
      type: Select,
      options: STATUSES,
    },
    {
      name: "description",
      message: "Add a status descript status",
      type: Input,
    },
  ]);

  await update(osbRepoPath, {
    instanceId,
    status: status,
    description: description,
  });

  console.log(`Updated status of instance ${instanceId} to '${status}'`);
}