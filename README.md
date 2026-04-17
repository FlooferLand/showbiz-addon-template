# Showbiz Addon Template

This is an official addon starter project for the [Showbiz mod](https://github.com/FlooferLand/showbiz)


## Preamble

Please read this whole document carefully, as there's a bunch of things you need to do to set this up.

This project uses the VSCode IDE for the main part, which is a bit complicated as it's mainly meant for programmers.

Some important things you need to know:
- VSCode has a task system, which are little pieces of code that you can run at any time. You can trigger a task with `CTRL + T` _(or `Terminal -> Run Task`)_ and type its name there

## Setup

1. Download this template as a zip and extract it
2. Download and install [VSCode](https://code.visualstudio.com/download)
3. Open VSCode, and open the `ShowbizAddon.code-workspace` file from inside of that newly extracted folder via `File -> Open Workspace from File`
4. Install the recommended extensions when it prompts you to do so in the bottom right _(you can see them and install them manually in the `ShowbizAddon.code-workspace` if it doesn't pop up for you)_
5. Run the `showbiz: install` task in order to automatically install the Showbiz mod and it's dependencies into `run/`
6. Head over to `Run` on the top and hit `Start Debugging` _(or simply press F5)_


## Reloading assets

Using this template, you can actually reload assets without restarting the game.

After you changed an asset, such as a bot model, focus back on VSCode and hit `CTRL + Shift + B` to rebuild _(or use the `showbiz: rebuild` task)_

This bakes the asset changes back into the jar.

You can now hit CTRL + T inside Minecraft to reload the resource pack, or use `/reload` to reload the datapack, and your changes should be there!


## Building out the mod

Once your addon is finished, you can run the `showbiz: assemble` task

After it's done building, you can check the `./build/libs/` folder and your addon jar should be there!

You can upload this to Modrinth, or share it around in the [official Showbiz mod Discord](https://discord.gg/RyUBXKvBwq)!
