# Independent audit verdict: external correction boundary

Reviewed candidate: `build/later-source-20261116/1.21.1--1.21.3/external_correction_boundary/boundary.md`  
Candidate SHA-256: `605f2add736580b391f5cd4c91f2804cb9f83ba5e0ff7ac79f71a73ef15f1f4e`

## Verdict

**Confirmed for the assigned bounded claims.** The exact Mojmap source bodies support the reported adjacent boundary: the `ServerPlayer.teleportTo(double, double, double)` writer and `ClientPacketListener.handleMovePlayer` passenger gate differ in 1.21.1 versus 1.21.2, and the inspected 1.21.2 behavior persists in 1.21.3. This is not pair-freeze or full caller/eligibility acceptance.

The candidate's acknowledgment and response-packet facts are correct, with one ordering detail worth retaining: 1.21.1 sends `ServerboundAcceptTeleportationPacket` before `ServerboundMovePlayerPacket.PosRot`; 1.21.2 and 1.21.3 send `PosRot` before teleport acceptance. The 1.21.2/1.21.3 ordering remains outside the passenger guard, so riding still skips the correction state write while both packets are sent from the unchanged local player state.

## Identity and source integrity

- Candidate bytes independently hash to the assigned SHA-256.
- 1.21.1 and 1.21.3 root ready markers declare `status=ready`, their exact `versionId`, and `mapping=mojmap`. Their bound source/artifact manifest digests match the sidecars. The checked relevant Java-file hashes match both the actual source bytes and their source-manifest entries.
- 1.21.2 has no ready marker or standalone root source/artifact manifests. Its mapping-local `decompiled_minecraft/1.21.2/mojmap/.decompile-provenance.json` hashes to `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` and contains 5,654 path-hash entries. It has no top-level release or mapping field. The identity caveat is adequately closed for this bounded audit by the canonical `1.21.2/mojmap` location, the pinned exact `.../1.21.2.json` metadata input (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), the pinned Mojang client-mappings `client.txt` input, and matching hashes for every relevant checked source file. No remaining release/namespace identity gate was found for these claims.
- The 1.21.2 and 1.21.3 checked source hashes are identical for `ServerPlayer.java`, `ClientPacketListener.java`, `LocalPlayer.java`, `ClientboundPlayerPositionPacket.java`, `Relative.java`, and `PositionMoveRotation.java`.

## Bounded source findings checked

1. **Correction writer:** 1.21.1 `ServerPlayer.teleportTo` uses the absolute XYZ overload and only `RelativeMovement.ROTATION`. 1.21.2 and 1.21.3 use `PositionMoveRotation(Vec3(x,y,z), Vec3.ZERO, 0, 0)` and `Relative.union(Relative.DELTA, Relative.ROTATION)`. In `Relative`, `DELTA` expands to the three delta components plus `ROTATE_DELTA`. `PositionMoveRotation.calculateAbsolute` adds the packet's zero delta to existing velocity for those delta flags; relative zero rotations produce no rotation delta. The candidate's non-passenger velocity-preservation interpretation is supported.
2. **Correction receiver:** 1.21.1 applies correction values to `minecraft.player` without an `isPassenger()` guard. 1.21.2 and 1.21.3 wrap that state update in `if (!player.isPassenger())`; packet response and acceptance sends remain after the branch. The listener is the correction receiver; searches in all three hash-checked `LocalPlayer.java` files found no corresponding packet handler/helper references.
3. **Not audited:** server-side API callers, event conditions, packet-send eligibility, riding-state transitions, and downstream snapshot consumers remain open as stated in the candidate.

## Evidence hashes

| Source file | 1.21.1 | 1.21.2 | 1.21.3 |
|---|---|---|---|
| `ServerPlayer.java` | `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` |
| `ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |
| `LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `ClientboundPlayerPositionPacket.java` | `03f4c58cb1f801cb62fe48a207a575b7bfd2a9fdcb5a0683bef3c0c9c75bed69` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` |
| Relative type | `RelativeMovement.java`: `7b12db600cde348858fe5835d7618a59b22ad44ed147351fc79b83b7ecaced4f` | `Relative.java`: `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` | `Relative.java`: `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` |
| `PositionMoveRotation.java` | absent | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` |

No build, tests, decompile, game launch, online mapping lookup, product/canonical/backlog write, or pair-freeze action was performed.

