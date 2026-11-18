# Independent source audit: 1.12.1–1.12.2 Llama rider placement

## Verdict

**ACCEPTED — bounded no-difference claim.** For an ordinary local player riding the adult default Llama, the direct rider-position callback, Y expression, numeric promotion/order, and applicable callback guards are unchanged between 1.12.1 and 1.12.2. The draft’s source-identity objection is resolved: 1.12.1 provenance exists inside the source root as hidden `.decompile-provenance.json`; it was missed by looking only for sidecars at the version parent. This verdict covers only the operation and dependencies named here; it does not freeze the complete 1.12.1–1.12.2 movement pair or establish an intermediate patch boundary.

## Frozen input

Audited packet: `c-llama-1121-1122-position-intermediate/report.md`, SHA-256 `F1C54C6ECFC1E03077A4077E417878D3D8B336A5BDC3CF3A80113F5D520125D0`.

## Exact source identity and integrity

- **1.12.1 / `ornithe-feather` (Feather):** `decompiled_minecraft/1.12.1/ornithe-feather/.decompile-provenance.json`, SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. It records the `lpc-decompile-v1` pipeline, Windows 11 / Java 25.0.3 runtime, tool JAR hashes, and 36 hashed inputs. The inputs identify client `1.12.1` (`piston-meta` URL ending `/1.12.1.json`, client object `83385d…`) and Feather metadata URL ending `/feather/1.12.1`; mapping artifact is `feather-gen2-1.12.1+build.2-mergedv2.jar` (input SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`). Its embedded source inventory has 2,050 entries; all 2,050 current Java files exist and match their recorded hashes. The three audited files match the packet’s source hashes.
- **1.12.2 / `ornithe-feather` (Feather):** parent-root `ornithe-feather.provenance.json` identifies exact version `1.12.2`, namespace, run, and Feather Gen2 build 2 artifacts. `ornithe-feather.ready.json` reports `status=ready`, version `1.12.2`, mapping `ornithe-feather`, 2,050 files, and client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`. Its source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and artifact-manifest SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` match the current parent-root manifest bytes. All 2,050 manifest entries match the current source tree. The mapping artifact hashes in `artifacts.sha256` are `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` (mergedv2 JAR) and `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58` (Tiny).

Audited metadata byte hashes: 1.12.2 provenance `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; ready marker `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`.

## Bounded operation evidence

All source paths below are relative to the version’s `ornithe-feather/` root. The relevant source-file hashes are equal across the pair and independently match both source inventories:

| Source | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

Both releases have the same Llama declaration and default dimensions at `LlamaEntity.java:50–63`; `updateRiderPositon(Entity)` at `:144–151` first checks `hasPassenger(rider)`, then sets rider position using `x + 0.3F * g`, `y + getMountHeight() + rider.getRideHeight()`, and `z - 0.3F * f`. `getMountHeight()` at `:153–156` returns `height * 0.67`. This Llama override does not call `HorseBaseEntity.updateRiderPositon`; the superclass’s `MobEntity`/angry-animation branches at `HorseBaseEntity.java:945–961` therefore do not replace this player placement path.

`Entity.java:1582–1601` is byte-identical. `rideTick()` zeroes velocity, ticks, and, if still riding, dispatches `entity.updateRiderPositon(this)`; the base callback separately guards with `hasPassenger`. `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`; its `rideTick()` delegates to `super.rideTick()` in both releases. This reaches the same mount callback for an ordinary local rider.

`PlayerEntity.java` differs as a whole (`1.12.1`: `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640`; `1.12.2`: `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`), but `getRideHeight()` at `:943–945` is textually identical and returns the `double` literal `-0.35` in each. Thus the Y calculation remains left-associative `double` arithmetic: `llamaY + (llamaHeightFloat * 0.67D) + (-0.35D)`. Float `height` is promoted for multiplication by the `double` literal; the result and player offset are then added to the `double` Y. No Y cast, reordered intermediate, or guard change is present.

## Scope limit

This accepts the packet’s single placement-operation no-difference observation and closes its exact-source identity gate. It is not full tick/inventory coverage, full-pair freeze, runtime parity evidence, or proof of when any behavior changed between these endpoints.
