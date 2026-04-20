# Showbiz Addon Template

This is an official addon starter project for the [Showbiz mod](https://github.com/FlooferLand/showbiz)


## Preamble

Please read this whole document carefully, as there's a bunch of things you need to do to set this up.

This project uses the VSCode IDE for the main part, which is a bit complicated as it's mainly meant for programmers.

Some important things you need to know:
- VSCode has a task system, which are little pieces of code that you can run at any time. You can trigger a task through `Terminal -> Run Task`, typing its name there, and pressing enter.

## Setup

1. [Download this template as a zip](https://github.com/FlooferLand/showbiz-addon-template/archive/refs/heads/main.zip) and extract it. _(if you're more familliar with Git, I recommend hitting the "Use this template" button and making a fork)_
2. Download and install [VSCode](https://code.visualstudio.com/download)
3. Open VSCode, and open the `ShowbizAddon.code-workspace` file from inside of that newly extracted folder via `File -> Open Workspace from File`, then say yes and install anything it prompts you to install.
4. After every extension has finished installing, press CTRL + Shift + P and type ">JDK". You should see something along the lines of "Download, Install, and use JDK". Select that, then in the JDK downloader panel click "Other options", select JDK 21, click "Install selected JDK", and install it into a folder called `java` at the root of the workspace.
5. Run the `showbiz: install` task in order to automatically install the Showbiz mod and it's dependencies into `run/`
6. Head over to `Run` on the top and hit `Start Debugging` _(or simply press F5)_

### Setting up your addon
1. Open `gradle.properties` and replace all the placeholders there with your addon's details, *this is mandatory*.
   - Make sure your addon namespace is using `snake_case`. `My Addon` is not a valid namespace, however `my_addon` is. _(this is the same with folder names)_
2. Make sure to rename the first folder inside the assets and data folders _(inside `src/main/resources/`)_ to your addon's namespace, for example instead of being `assets/example/..` it should be `assets/my_addon/..` _(but with the addon namespace you set earlier)_

Please read the [Showbiz documentation](https://github.com/FlooferLand/showbiz/wiki) for more information about addon creation.


## Reloading assets

Using this template, you can actually reload assets without restarting the game.

After you changed an asset, such as a bot model, focus back on VSCode and hit `CTRL + Shift + B` to rebuild _(or use the `showbiz: rebuild` task)_

This bakes the asset changes back into the jar.

You can now hit CTRL + T inside Minecraft to reload the resource pack, or use `/reload` to reload the datapack, and your changes should be there!


## Building out the mod

Once your addon is finished, you can run the `showbiz: assemble` task

After it's done building, you can check the `./build/libs/` folder and your addon jar should be there!

You can upload this to Modrinth, or share it around in the [official Showbiz mod Discord](https://discord.gg/RyUBXKvBwq)!
