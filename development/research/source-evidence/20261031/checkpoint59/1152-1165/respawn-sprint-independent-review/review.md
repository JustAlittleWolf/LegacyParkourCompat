# Independent review: respawn sprint attribute receiver

## Verdict

**ACCEPT.** Candidate SHA-256 `B526514868C4CD32237CD8B3D62EC4DEF3B2B957531B6F6BA0AA3E0FFEBA7435` and source report SHA-256 `3B5A962D447C96C87F9ADE3C417D37DE9B20F01DD23F05C338A7B790AE0B0807` accurately bound a direct client receiver difference and a conditional movement-input consequence. The claim does not need a producer route, server timing, runtime trajectory, or packet frequency premise.

## Independent checks

- Rehashed all 36 rows in `respawn-sprint-receiver/source-hashes.tsv` against the exact version source files and each release's own source manifest. **36/36 match.** Sidecar `mojmap.sources.sha256` entries were checked for the sidecar-published releases; the 1.16.3 and 1.16.4 source entries were checked against their mapping-root `.decompile-provenance.json` maps. The inventory hash remains `252a251077103f526a57e7a60799723b436fb79fd6cbfbc50e2771908064108e`.
- Checked the receiver and constructor boundary in 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5. 1.15.2 copies entity data then attributes unconditionally. Release `1.16` first copies entity data then guards the attribute copy with `shouldKeepAllPlayerData`; the gate persists in every checked later release. The new constructor receives the old sprint value as `wasSprinting` (a local send-state field); it does not call `setSprinting`. Subsequent entity-data assignment copies the shared sprint flag. The order therefore retains the true sprint bit while leaving the new default movement-speed attribute map without the old sprint modifier when the packet flag is false.
- Checked the attribute interaction: `setSprinting(true)` installs the `+0.3` multiply-total movement-speed modifier, so the stated ordinary base `0.1` yields `0.13`. `resetPos` clears velocity but does not set `onGround`; the replacement starts with `onGround=false`. Under the stated flat-floor and no-horizontal-obstruction setup, the first tick's airborne speed is sprint-flag-derived `0.026` on both paths, the downward move collides with the floor and sets `onGround=true`, and no stated local sprint-stop gate fires. On the next tick, unchanged qualifying forward input and food keep sprinting; the grounded friction branch uses the movement speed in `moveRelative` before collision clipping. With no intervening movement-speed attribute update, the old and new values are respectively `0.13` and `0.1` multiplied by the same friction factor.
- Ordinary local player travel is the affected movement consumer. The report correctly conditions the result on equal input state and no intervening speed-attribute update and stops at the pre-clipping movement input. It does not infer a full position trajectory.

## Review notes

No substantive revision is required. The candidate's phrase “no water/swimming, horizontal collision, item use, or blindness” is read as a negated list; “no horizontal collision” would make that gate list unambiguous if the draft is edited later. The source report already states that condition clearly.

## Exact source path references checked

- `1.15.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`: `handleRespawn`, entity-data assignment, unconditional attribute assignment, then `resetPos`.
- `1.16` through `1.16.5` at the same listener path: entity-data assignment followed by `shouldKeepAllPlayerData()` attribute gate, then `resetPos`.
- `LocalPlayer.java` in both endpoint families: constructor stores `wasSprinting`; no constructor call restores sprinting. `sendPosition`'s later comparison of `isSprinting()` to `wasSprinting` is unrelated to restoring the attribute modifier.
- `LivingEntity.java`, `Player.java`, `Entity.java`, attribute classes, and `MultiPlayerGameMode.java` listed in the 36-row source inventory: sprint modifier/value, movement speed selection, friction/travel consumer, reset/collision state, and player creation/ability adjustment.

No product, test, build, runtime, or canonical source file was changed.
