# Independent audit: raw-bytecode supplement for the 1.8 movement-speed cap boundary

## Scope and verdict

**Verdict: ACCEPT, bounded to the stated operation and release interval.** I independently checked the raw-bytecode supplement `.native-source/20261028/1818-speed-boundary-raw-bytecode/REPORT.md`; its SHA-256 is `1edb89f388e395d96ed75df2c86066b57f7fdd1c07fb5518e260539d2a642032`, matching its adjacent SHA-256 sidecar. This audit verifies exact 1.8 and 1.8.1 Mojang client inputs, their pinned Feather Gen2 build.2 mappings, and the cited raw-bytecode behavior. It then binds that evidence to the independently accepted 1.8.2–1.8.4 source closure and the 1.8.4–1.8.8 cutover audits.

The evidence supports an uncapped `generic.movementSpeed` range for 1.8.0–1.8.4 and a maximum of 1024 beginning at 1.8.5. It does not establish whole-movement parity, all attribute producers/consumers, runtime behavior, or any interval outside those specifically audited releases.

## Input identity and mappings

I recomputed the exact local hashes and confirmed the pin URLs/digests in `.minecraft-decompile-cache`:

| Release | Version metadata SHA-256 | Mojang client SHA-1 / SHA-256 | Feather Gen2 build.2 mergedv2 SHA-256 | embedded Tiny SHA-256 |
|---|---|---|---|---|
| 1.8 | `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` | `d722504db9de2b47f46cc592b8528446272ae648` / `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `0db2fa38dc74a0981164e53fc73152291cea5153e705b5d06d001ede01b682e8` |
| 1.8.1 | `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149` | `6edd2a3e3d76a7602c52f319f87cf11f7720b43b` / `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` |

Both metadata pins identify their exact Mojang version JSON package and both client pins identify the SHA-1 object named by `downloads.client` in that JSON. The Feather artifact pin files name the corresponding exact Ornithe Maven `feather-gen2/<version>+build.2/...-mergedv2.jar` artifact. I opened each mapping JAR read-only and independently hashed `mappings/mappings.tiny`; both headers declare the ordered namespaces `official`, `intermediary`, `named`. The class rows for the cited operation classes bind the raw obfuscated class names to stable intermediary IDs and the corresponding named classes. This confirms the raw official class selection independently of the cached `client-ornithe-feather.jar` output; those mapped outputs have no output provenance and were not used as evidence.

## Raw bytecode checks

Using the pinned JDK 25 `javap -p -c` against only the two exact raw client JARs, I verified:

- The `EntityAttributes` static initializer's `generic.movementSpeed` entry passes `0.699999988079071d` (the widened `0.7F`), `0.0d`, and `1.7976931348623157E308d` to the mapped ranged-attribute constructor in both releases.
- `RangedEntityAttribute`'s double-value method passes the supplied value and stored lower/upper bounds to the corresponding `MathHelper.clamp` method.
- `ModifiableEntityAttributeInstance` computes base plus operation-0 additions, adds operation-1 modifiers multiplied by the operation-0 intermediate, applies each operation-2 `(1 + amount)` multiplier, and only then invokes the attribute's clamp. The 1.8 and 1.8.1 instruction sequences agree in this ordering.
- The player `getSpeed` override in each raw class loads the movement-speed attribute, retrieves its computed double value, converts it with `d2f`, and returns a float.
- The mapped `LivingEntity.moveRelative(float,float)` method in both raw JARs has the cited ordinary grounded-land branch using the virtual player speed override and a separate airborne branch using `speedInAir`. Its water/Depth Strider branch reads the enchantment level, caps it at three, halves it off ground, and interpolates toward virtual `getSpeed()`.

These checks support the supplement's stated, limited bytecode observations. They do not rely on execution of any client class. The read-only `javap` invocations only disassembled classes.

## Adjacent accepted source evidence and cutoff

The 1.8.2–1.8.4 report hash is `3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411`; its independent audit hash is `be3d4127cbdc0b6031bd933cf470ec34a85678e8c2418621147baf8362d1ed99`. That accepted audit verifies the exact source files and ordinary player sinks, while expressly limiting its original source evidence to .2–.4. Its inspected attribute has `Double.MAX_VALUE`, operation ordering and final clamp match the raw .0/.1 sequence, and the relevant player speed sinks remain the same.

The 1.8.4–1.8.8 cutoff report and three audit hashes match the assigned immutable inputs:

- K report: `745bcede5b858c8e36c5bb7a7a26a3c9be3644c4fd3c301e786f3c34b2066fe1`.
- Endpoint audit: `c04690db3107b86ec39c2e35310fe732de770d7a180bb620009fbae0fe3ed06f`.
- Cutover audit: `762536da33858e7a0150936e14cfbd3b99ac36d2f16c6a9bd60b76a84cf4666a`.
- Full independent audit: `a08dcd79d9cd83d2824705863e7b345c6c1da349294c153ea05c07249c27485e`.

Those independently accepted artifacts establish `Double.MAX_VALUE` at 1.8.4 and the first 1024 upper bound at 1.8.5, persisting across .6–.8 for the inspected calculation and movement sinks. Thus the combined bounded evidence closes the formerly missing .0/.1 endpoints without extending the range beyond .4 for uncapped behavior or beyond the audited .5–.8 chain for the cap.

## Limitations and process

This is source/bytecode evidence, not runtime parity. Attribute producers and all possible consumers are not exhaustively enumerated here. The ordinary grounded land and Depth Strider sinks are directly bound; the ordinary airborne land path reads `speedInAir`, so no claim is made that this cap changes that path. No source generation, decompiler, Gradle, tests, game/runtime, network lookup, or cache/canonical-source modification was performed.
