# Independent audit verdict — 1.21 / 1.21.1 active Elytra at climbable feet

## Verdict

**Accept the bounded no-difference result.** The frozen report's central claim is supported: for a dry, ordinary local player whose chest slot contains an enabled Elytra and whose synchronized fall-flying flag is already true, the paired player-side route has identical source in 1.21 and 1.21.1. The glide branch has no climbable rejection; it caps fall distance before calculating glide motion and before the first move. Climbable fall-distance/velocity cleanup occurs in the ordinary movement branch, after the glide branch has been ruled out.

This accepts only the reviewed route and its stated boundary. It does not close full-pair coverage or establish block-shape parity outside the examined player-side path.

## Frozen input

Reviewed `c-glide-121-1211-cap-route/report.md`, SHA-256 `3d551a0c9b21b712dd3b6bf73e1d063e4f99ac16d42b48fbd01719d29694c10` (9881 bytes at audit). The report's input identity is frozen by these bytes.

## Exact source identity and integrity

Both sides are canonical official Mojang-mapped (`mojmap`) decompilations in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- **1.21:** `1.21/mojmap/.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. Its exact inputs include release metadata `1.21.json` (`cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`), client jar (`4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`), and official client mappings `client.txt` (`127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`).
- **1.21.1:** `1.21.1/mojmap.provenance.json`, SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`, identifies version `1.21.1`, namespace/mapping option `mojmap`, and Vineflower 1.12.0. `mojmap.ready.json` SHA-256 is `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; its ready status and source-manifest digest were checked. The current `mojmap.sources.sha256` digest is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, matching the ready marker; `artifacts.sha256` also matches its ready-marker digest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. The manifest pins the 1.21.1 client jar (`499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`), official client mappings (`140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`), and mapped client jar (`6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe`).

I recomputed the direct-file SHA-256 values and checked them against each side's source pins. All four files are byte-identical across the pair:

| Mojmap source relative to each source root | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

The compared bodies are present and intact in both trees; direct inspection confirms the report's cited line ranges and operation order.

## Route audit

- **Start edge:** `LocalPlayer.java:741–748` blocks a *new* start when `onClimbable()` is true and otherwise checks the chest Elytra. `Player.java:1529–1543` checks airborne/not already gliding/not in water/no levitation/enabled chest Elytra, then sets shared flag 7. This start gate does not clear a flag that was already true.
- **Active-state update:** `LocalPlayer.java:804` enters `super.aiStep()`. `LivingEntity.java:2665–2679` calls `updateFallFlying()` before travel. At `2716–2740`, the updater retains a true flag only while airborne, not a passenger, without levitation, and with enabled chest Elytra. It contains no climbable check. Its shared-flag write is guarded by `!level().isClientSide`; this updater does not write the flag on the local client.
- **Dry movement branch and cap:** `LivingEntity.java:2091–2152` shows local-controlled movement first taking water/lava paths when their predicates apply; for the specified dry case, an active Elytra reaches `isFallFlying()` at `2151`. `checkSlowFallDistance()` at `2152` is the first statement in that branch. `Entity.java:2386–2390` sets `fallDistance` to exactly `1.0F` only when vertical velocity is greater than `-0.5` and current fall distance exceeds `1.0F`. The cap precedes glide calculations and `move(MoverType.SELF, ...)` at `LivingEntity.java:2176–2177`.
- **Landing/final edge:** The glide branch's on-ground shared-flag clear at `LivingEntity.java:2188–2190` is server-only. `Entity.move` calls `checkFallDamage` at `Entity.java:645–648`; for a player, dispatch first passes through the `LivingEntity` override at `LivingEntity.java:327–359`, which delegates to `Entity.checkFallDamage` (`Entity.java:1163–1178`). The report's landing statement is correct, with this virtual-dispatch detail made explicit here.
- **Climbable cleanup:** Only after the glide branch does `LivingEntity.travel` enter the ordinary branch (`2191–2212`). That calls `handleRelativeFrictionAndCalculateMovement` (`2252–2262`), which applies `handleOnClimbable` before the ordinary move (`2253–2255`). The helper resets fall distance and clamps horizontal/downward movement (`2280–2294`). `onClimbable()` (`1517–1531`) reads the current in-block state and accepts the climbable tag or a usable trapdoor. Thus ordinary versus ladder/vine feet can change this predicate and ordinary-branch cleanup, but does not insert cleanup into the already-selected glide branch.
- **Geometry boundary:** The paired `Entity.java` and `LivingEntity.java` implementations, including the generic move call boundary, are byte-identical. The actual collision result still depends on the selected block state's shape and collision data; this bounded review does not claim an independent inventory of ladder/vine shape providers or block-tag resource payloads. Those dependencies can affect resolved displacement/on-ground state but do not change the reviewed branch/cap ordering.

No material correction to the frozen result is required. The only precision addition is the player-specific `LivingEntity.checkFallDamage` override in the terminal landing path, which delegates to the cited `Entity` implementation.
