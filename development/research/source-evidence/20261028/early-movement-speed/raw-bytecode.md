# Raw-bytecode supplement: 1.8.0–1.8.1 movement-speed attribute boundary

## Scope and method

This read-only supplement checks the exact Mojang 1.8 and 1.8.1 client class files directly, without relying on the cached `client-ornithe-feather.jar` outputs, whose output provenance is absent. It is limited to the `generic.movementSpeed` declaration, ranged clamp, modifier calculation, the player `getSpeed` override, and its ordinary land/Depth-Strider movement consumers. It does not claim a full source inventory or runtime parity.

Tool: `C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot\bin\javap.exe`; invocation: `javap -classpath <exact-pinned-client.jar> -p -c <raw-official-class>`. The class files were disassembled only; no class was executed. The already-cached Feather mapping artifacts and Tiny entries were read and hashed; no mappings, sources, caches, or canonical decompile outputs were generated or modified.

## Exact input and mapping identity

The local exact release metadata IDs and SHA-256 values match the cache pins. The Mojang client JAR SHA-256 values likewise match local pins and the `downloads.client` SHA-1 object identities recorded by those version JSON files.

| Release | Version JSON package / SHA-256 | Client artifact SHA-1 / SHA-256 | Feather mapping artifact / SHA-256 | Embedded `mappings/mappings.tiny` SHA-256 |
|---|---|---|---|---|
| 1.8 | `1.8` / `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593`; package `9eb165eef46294062d8698c8a78e8ac914949e7a/1.8.json` | `d722504db9de2b47f46cc592b8528446272ae648` / `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `1.8+build.2-mergedv2.jar` / `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `0db2fa38dc74a0981164e53fc73152291cea5153e705b5d06d001ede01b682e8` |
| 1.8.1 | `1.8.1` / `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149`; package `62f9f77f67fd7d6c92cfae57cecd445be14ccd4e/1.8.1.json` | `6edd2a3e3d76a7602c52f319f87cf11f7720b43b` / `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `1.8.1+build.2-mergedv2.jar` / `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` |

The cached Feather metadata pins select stable build `.2` for each release and match their exact `/1.8` and `/1.8.1` release endpoints (metadata SHA-256 `83ac5ccbc65ec6496a6b4a54413ee22082f60bc99c74ea7b20f68b97acc0be7b` and `40754754c7133b6e01354a49e8c8ba6be2b16bac02adc881057831b6b543b7c9`, respectively). Each mapping artifact pin names the exact Ornithe Maven `feather-gen2/<release>+build.2/...-mergedv2.jar` artifact and its hash in the table. Both embedded Tiny files declare namespaces `official`, `intermediary`, `named` in that order.

For each inspected class, the Tiny entry's intermediary class ID is the same between releases while its official raw class name changes. This establishes correspondence from each pinned raw JAR class to the named operation, without relying on the unbound mapped output JAR:

| Named class | 1.8 raw official / Tiny intermediary | 1.8.1 raw official / Tiny intermediary |
|---|---|---|
| `EntityAttributes` | `afs` / `C_82850287` | `afr` / `C_82850287` |
| `RangedEntityAttribute` | `yg` / `C_66730971` | `yj` / `C_66730971` |
| `EntityAttribute` | `xy` / `C_63962274` | `yb` / `C_63962274` |
| `ModifiableEntityAttributeInstance` | `ye` / `C_13795309` | `yh` / `C_13795309` |
| `MathHelper` | `uv` / `C_45510667` | `ux` / `C_45510667` |
| `LivingEntity` | `xm` / `C_74425583` | `xo` / `C_74425583` |
| `PlayerEntity` | `ahd` / `C_40996800` | `aha` / `C_40996800` |
| `EnchantmentHelper` | `aph` / `C_47465897` | `ape` / `C_47465897` |

## Bytecode observations

**Attribute declaration.** Tiny maps static field `d` on both mapped `EntityAttributes` classes to `MOVEMENT_SPEED`. In raw `afs.<clinit>` (1.8) and `afr.<clinit>` (1.8.1), the `generic.movementSpeed` entry constructs the ranged attribute with default `0.699999988079071d` (the widened `0.7F` value), lower bound `0.0d`, and upper bound `1.7976931348623157E308d` (`Double.MAX_VALUE`). The same initializer marks the attribute modifiable. The relevant instruction sequence and constants are the same in both raw JARs. There is no 1024 upper bound in either release.

**Clamp and modifier order.** `RangedEntityAttribute.a(double)` calls the mapped `MathHelper.clamp(double,double,double)` with the value and its stored minimum/maximum. The clamp bytecode returns the minimum when the input compares below it, the maximum when above it, otherwise the input. The range constructor stores those bounds and rejects `min > max`. `ModifiableEntityAttributeInstance.g()` obtains base value, adds all operation-0 modifiers, then adds each operation-1 modifier times that operation-0 intermediate, then multiplies by each `(1 + operation-2 modifier)`, and finally calls the attribute clamp once. The corresponding 1.8 and 1.8.1 raw method instruction sequences match semantically and in arithmetic order; only obfuscated class names differ.

**Player speed read.** The 1.8 raw `PlayerEntity.ahd.bH()` and 1.8.1 `PlayerEntity.aha.bI()` both load the `MOVEMENT_SPEED` static field from their corresponding `EntityAttributes` class, call the inherited attribute lookup, invoke the attribute instance's computed-value method, convert double to float (`d2f`), and return. Tiny lists `getSpeed` on the superclass and does not separately list this overriding method, but raw class correspondence is unambiguous: the mapped superclass slot and player override share the exact JVM name/descriptor, and the override's referenced attribute is the mapped movement-speed field. This matches the canonical 1.8.2 Feather source operation.

**Movement consumers.** Raw `LivingEntity.moveRelative(float,float)` in both JARs includes the same calls from the water/Depth-Strider path to `EnchantmentHelper.getDepthStriderLevel` and virtual `getSpeed`, including the level cap at 3 and the off-ground halving branch. In the ordinary grounded land path the same method calls virtual `getSpeed()` and scales it by the ground-friction factor; the airborne land branch reads `speedInAir` instead. The paired 1.8 and 1.8.1 bytecode calls target the virtual player override above, preserving the computed attribute result at the in-scope speed consumers.

## Comparison with accepted source evidence and bounded conclusion

The canonical 1.8.2–1.8.4 source closure (`.native-source/20261028/1818-speed-boundary/REPORT.md`, SHA-256 `3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411`; independent audit SHA-256 `be3d4127cbdc0b6031bd933cf470ec34a85678e8c2418621147baf8362d1ed99`) records the same default/min/max, operation-0/1/2 calculation and final clamp, player double-to-float speed read, and ordinary ground and Depth-Strider consumers. This raw supplement independently establishes those bounded operations in exact 1.8.0 and 1.8.1 inputs.

The accepted exact 1.8.4–1.8.8 cutover report and audits establish the first cap at 1.8.5: 1.8.4 has maximum `Double.MAX_VALUE`; 1.8.5 first has maximum `1024.0`; the relevant calculation and movement consumers remain source-identical across that chain. References: `.native-source/20261028/frontier-c/K/1.8.4--1.8.8.md` SHA-256 `745bcede5b858c8e36c5bb7a7a26a3c9be3644c4fd3c301e786f3c34b2066fe1`; `K-audit/audit.md` SHA-256 `c04690db3107b86ec39c2e35310fe732de770d7a180bb620009fbae0fe3ed06f`; `K-cutover-audit/audit.md` SHA-256 `762536da33858e7a0150936e14cfbd3b99ac36d2f16c6a9bd60b76a84cf4666a`; `K-full-audit/independent-audit.md` SHA-256 `a08dcd79d9cd83d2824705863e7b345c6c1da349294c153ea05c07249c27485e`.

**Bounded conclusion:** raw bytecode and exact pinned mapping identity establish that 1.8.0 and 1.8.1 have no movement-speed attribute cap at 1024, matching the accepted 1.8.2–1.8.4 source behavior for the inspected operations. Combined with the separately accepted 1.8.4→1.8.5 source cutover, this supports the movement-speed maximum interval as 1.8.0–1.8.4 uncapped, then capped at 1024 beginning 1.8.5. This is a bounded source/bytecode comparison, not full movement-dependency closure, a pair freeze, or runtime parity evidence. No decompiler, Gradle, tests, or game/runtime was launched; no cache or canonical source was modified.