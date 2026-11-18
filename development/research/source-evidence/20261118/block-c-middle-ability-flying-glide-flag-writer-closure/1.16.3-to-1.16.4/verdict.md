# Independent verdict: 1.16.3 → 1.16.4 creative flight over active Elytra

## Verdict and bounded scope

**Accepted as a source-confirmed no-difference result for the assigned operation.** The independent trace agrees with the pinned source report: in an ordinary local, dry, airborne, nonpassenger player state with an enabled default chest Elytra, flag 7 active, and lawful creative flight (`abilities.flying`), `Player.travel` delegates into the Elytra branch, completes its movement, then separately resets `fallDistance` and clears shared flag 7. The gates and operation order match in 1.16.3 and 1.16.4. No difference claim or version cutover is supported for this operation.

This verdict closes only this bounded source claim. It does not establish complete pair coverage, an exact intermediate patch boundary, or runtime parity.

## Identity and provenance checked

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, with exact Mojmap trees under `1.16.3\mojmap` and `1.16.4\mojmap`. The project decompiler guide identifies this as the default shared root derived from the primary checkout's Git directory/commondir; no output-root override was used in this audit. The hidden local cache root is `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache`.

- 1.16.3 `mojmap/.decompile-provenance.json` SHA-256: `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. It pins client JAR `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` and official Mojmap `client.txt` `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4 `mojmap/.decompile-provenance.json` SHA-256: `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. It pins client JAR `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` and official Mojmap `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.
- I checked the local `.minecraft-decompile-cache\<version>\client.jar.pin.json` and `client_mappings.txt.pin.json` against the actual cached file hashes for both releases; all four cached files matched their pins and the provenance manifests.
- The independently examined `Player.java` bytes match each manifest's source entry and each other: SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- The independently examined `LivingEntity.java` bytes match each manifest's source entry and each other: SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- The local-player dispatch source `client/player/LocalPlayer.java` is also byte-identical in both manifests and on disk: SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.

The source-only report compared after completing the independent trace is `c-mid-1163-1164-ability-flight-glide-clear-source/report.md`, SHA-256 `A098E57895AE4CF02271CFC3978683237343150395456DF8C08697742AD68407`. Its exact input identities, source hashes and operation trace agree with this verdict.

## Independent source trace

In both releases, `LivingEntity.aiStep` calls `updateFallFlying()` before `travel(...)` (LivingEntity.java:2453–2460). In the stated airborne, nonpassenger state, the active flag and enabled Elytra retain fall-flying on the logical client; `updateFallFlying` only writes the shared flag on the logical server (2478–2496). `isFallFlying()` reads shared flag 7 (2891–2893). The dry precondition bypasses the water and lava branches.

`Player.travel` takes its `abilities.flying && !isPassenger()` branch (Player.java:1402). It saves vertical velocity and flying speed, adjusts flying speed, and invokes `super.travel`. With flag 7 active, `LivingEntity.travel` enters its fall-flying branch (1979–2021); for incoming vertical velocity greater than `-0.5`, it first writes `fallDistance = 1.0F`, applies glide math, attenuates velocity, and calls `move(MoverType.SELF, ...)` (1980–2008). The branch's own flag clear requires both `onGround` and a non-client level, so it does not clear the flag in the stipulated airborne local-client case.

After `super.travel` returns, `Player.travel` restores vertical velocity from the saved value times `0.6`, restores flying speed, writes `fallDistance = 0.0F`, then invokes `setSharedFlag(7, false)` (1407–1411). The fall-distance reset is not the flag clear. Both writes occur after the delegated move and both versions contain the same body and gates.

## Signed scope

Independent source audit, limited to the operation and preconditions stated above. Evidence is bound to the exact manifest hashes, source hashes and source-report hash in this file. No product, wiki, backlog, Git, build, test, generation, network or runtime action was performed.
