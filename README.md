
# Exit Stonecutter Template

For mods whose code changes little between Minecraft versions, Stonecutter makes developing Iris compatibility
add-ons easier.

A Fabric mod template that includes the [Exit library (emergency-exit)](https://github.com/Shader-Escape/emergency-exit)
by default.

The template is based on Minecraft 26.3 and uses [Stonecutter](https://stonecutter.kikugie.dev/) to build several
Minecraft versions from one codebase, across the 1.21.11 line:

| Minecraft | Obfuscated | Loom plugin | Dependencies | Java |
|---|---|---|---|---|
| ≤ 1.21.11 | Yes | `net.fabricmc.fabric-loom-remap`, `mappings(loom.officialMojangMappings())`, output goes through `remapJar` | `modImplementation` | 21 |
| 26.x | No | `net.fabricmc.fabric-loom`, no mappings, no remapping | `implementation` | 25 |

`build.gradle.kts` switches between the two by the current Stonecutter version, so you do not need a separate build
script per version.

Included versions: `1.21.1`, `1.21.11`, `26.3` (the main version: the source is written for 26.3).

## Layout

```
settings.gradle.kts           version list (stonecutter { versions(...) })
stonecutter.gradle.kts        active version (stonecutter active)
build.gradle.kts              build script shared by all versions
gradle.properties             mod id, name, version, Fabric Loader version
versions/<version>/gradle.properties  Minecraft, Fabric API and Exit versions for each version
src/main/java/.../ExampleMod.java              entrypoint
src/main/java/.../mixin/LevelRendererMixin.java  where Exit is hooked in
```

## License

[CC0 1.0](LICENSE).

---

# Exit Stonecutter 模板

针对版本代码变化不大的模组,用stonecutter开发iris兼容附属更轻松。

这是一个 Fabric 模组模板，它默认引入[Exit 库(emergency-exit)](https://github.com/Shader-Escape/emergency-exit)。

模板以 Minecraft 26.3 为原型,用 [Stonecutter](https://stonecutter.kikugie.dev/) 让同一份代码构建多个 Minecraft
版本,并跨越 1.21.11 这条线:

| Minecraft | 混淆 | Loom 插件 | 依赖写法 | Java |
|---|---|---|---|---|
| ≤ 1.21.11 | 是 | `net.fabricmc.fabric-loom-remap`,`mappings(loom.officialMojangMappings())`,产物经过 `remapJar` | `modImplementation` | 21 |
| 26.x | 否 | `net.fabricmc.fabric-loom`,没有 mappings,不重映射 | `implementation` | 25 |

`build.gradle.kts` 按 Stonecutter 当前版本自动切换这两套写法,你不需要为每个版本写不同的构建脚本。

自带的版本:`1.21.1`、`1.21.11`、`26.3`(主版本,源码里写的就是 26.3 的代码)。

## 目录

```
settings.gradle.kts           版本列表(stonecutter { versions(...) })
stonecutter.gradle.kts        当前激活版本(stonecutter active)
build.gradle.kts              所有版本共用的构建脚本
gradle.properties             模组 id、名称、版本,Fabric Loader 版本
versions/<版本>/gradle.properties  每个版本的 Minecraft、Fabric API、Exit 版本
src/main/java/.../ExampleMod.java              入口类
src/main/java/.../mixin/LevelRendererMixin.java  接 Exit 的地方
```

## 许可证

[CC0 1.0](LICENSE)。
