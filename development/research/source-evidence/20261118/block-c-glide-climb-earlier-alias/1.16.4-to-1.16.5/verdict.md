# Independent source audit verdict: Elytra and climbable route, 1.16.4–1.16.5

## Bound input and evidence

- Frozen claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1164-1165-alias-gate/report.md`
- Frozen report SHA-256: `4290c8669b47b89dd594a7f24697e53a1a99f5adc7b35b64d701b2f857d5e9d1` (recomputed; exact match).
- Pair and mapping: exact adjacent releases `1.16.4 → 1.16.5`, Mojmap.
- `1.16.4` exact identity: `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`; version metadata is the exact `/1.16.4.json` with SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`, client JAR `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, Mojang mapping text `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and Vineflower 1.12.0 tool hash `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`.
- `1.16.5` exact identity: `decompiled_minecraft/1.16.5/mojmap.provenance.json` identifies `versionId: 1.16.5`, `namespace: mojmap`, exact run; adjacent `mojmap.ready.json` identifies the same version/mapping, the successful ready source output, 3,523 files, client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. Adjacent `artifacts.sha256` pins client Mojmap JAR `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`; the source-manifest file itself hashes to the ready marker's stated SHA-256. These metadata files sit in the `1.16.5/` directory next to `mojmap/`, not inside the source directory.
- Recomputed complete source-file hashes at both endpoints match the frozen report and each other: `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `ElytraItem.java` `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`. The 1.16.4 hashes also appear in its provenance; the 1.16.5 hashes appear in its source manifest.

## Claim audit

Accepted for the stated bounded path. The four complete paired class files are byte-identical, and direct inspection confirms the reported order and gates:

- In `LivingEntity.java`, `tick()` calls `aiStep()` (2123–2170); `aiStep()` scales input and calls `updateFallFlying()` before virtual `travel(...)` (2453–2459). The fall-flight counter increments or resets after the `aiStep()` path (2238–2242).
- `updateFallFlying()` retains an active flag only when airborne, unmounted, without Levitation, and wearing a fly-enabled chest Elytra (2478–2497). Durability and the final checked-flag write are server-only. `Player.tryToStartFallFlying()` has the separate launch guards (1520–1529); `startFallFlying()` sets flag 7, and `stopFallFlying()` writes true then false (1532–1538).
- In dry travel, the `isFallFlying()` branch precedes the ordinary friction branch (1979–2026). With the described local-player conditions, an already-active flag survives the updater and the glide branch executes even at climbable feet. Its pre-move test is the strict signed comparison `deltaY > -0.5`; when true it writes `fallDistance = 1.0F` before glide math and `move(...)` (1980–2008). The earlier fluid branches remain enclosing gates; dry air satisfies neither.
- `handleRelativeFrictionAndCalculateMovement()` applies the climbable response only in the later ordinary route (2062–2103). It is not reached for this active-glide route. Player movement statistics do check `onClimbable()` before `isFallFlying()` (Player 1437–1483), which affects classification after movement rather than selecting the movement branch.
- `Entity.move()` calls `checkFallDamage()` after collision resolution (485–518). A landing clears fall distance; otherwise a negative actual Y displacement is added (894–904), so the 1.0F pre-move assignment is not necessarily the final tick value. The client glide branch does not clear flag 7 on landing because that clear is server-gated (LivingEntity 2019–2021). On a later client update, the guards can compute `bl = false`, but the final shared-flag write is also server-only (2478–2497), so this path does not itself clear the local flag. `ElytraItem.isFlyEnabled()` uses `damageValue < maxDamage - 1` (ElytraItem 17).

## Verdict

The frozen no-difference claim is accepted for the bounded `1.16.4 → 1.16.5` Mojmap pair and its stated already-active, ordinary local-player, dry, enabled chest-Elytra path at ladder/vine feet. The report's source conclusions and four class hashes are confirmed. Provenance values are confirmed; note the 1.16.5 metadata files' adjacent version-root location as above. This verdict does not establish other launch, fluid, passenger, ability-flight, equipment, entity, release-boundary, or runtime behavior.

