# Independent Domain C yaw audit verdict

**Verdict: ACCEPT** for the bounded existing six-pair yaw slice. No difference was found in local stored-yaw arithmetic or the first ordinary dry relative-acceleration consumer. This is not a full movement-pair coverage or freeze verdict.

## Bound evidence

Immutable audit input commit: `2c155a088b9b19076dd1a658913c3bccdafa6e85`.

Frozen source roster, in order: `1.9.4 → 1.10 → 1.10.1 → 1.10.2 → 1.11 → 1.11.1 → 1.11.2` (all Ornithe Feather). Six reviewed pairs: `1.9.4--1.10`, `1.10--1.10.1`, `1.10.1--1.10.2`, `1.10.2--1.11`, `1.11--1.11.1`, `1.11.1--1.11.2`.

The retained source pins are defined by `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-audit/source-freeze.json` at the commit above (raw SHA-256: `afb2b5af2599a4ff624ebf6c6ac1cd222211e1e7aa0723e442059aecbc970ef2`). I checked every pinned source Java-file hash and every pinned provenance, ready-marker, and source-manifest hash where listed against the canonical local read-only tree; all available pins matched.

The following twelve Markdown payload blobs are bound to that same commit by raw SHA-256. Each value was recomputed from the exact `git show` blob bytes and matched its retained `.sha256` sidecar.

| Pair | Stored-yaw / dry consumer payload | Raw SHA-256 | Direct-turn payload | Raw SHA-256 |
|---|---|---|---|---|
| `1.9.4--1.10` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.9.4--1.10.md` | `dc4b57e1498a458d398eedd6713387eecf3d16ab50ba5d2b29394dcd4f0b07a6` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.9.4--1.10-direct-turn.md` | `8f64f52baa805acbf4d828fbc9fc1cfe45615855a33e74a95519affbc58228f0` |
| `1.10--1.10.1` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10--1.10.1.md` | `b3824ee4b2422bab17f79652bebb3014dd7edec6b4a668ee12ecb66c1cd67095` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10--1.10.1-direct-turn.md` | `1efbeb15550ab64537ac44c01512fed32baae7b3a86e2ea717f61819e142fe06` |
| `1.10.1--1.10.2` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10.1--1.10.2.md` | `eea6359e840e3eaf12eea287c492e422d6c1a1ae28b1593e4209b53df973d196` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10.1--1.10.2-direct-turn.md` | `b3cd6dbf73fc39de2ce3f676e437ad599ffafcac2a555e615914617f3e9480d8` |
| `1.10.2--1.11` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10.2--1.11.md` | `0414cb1a179be7b8d507244df5220c06f74ee5a1eb0e2edda1f83e32ea41bcce` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.10.2--1.11-direct-turn.md` | `ee168df53a5c7387fd023fb6784c02efd4dbcef920a8c18ca28d6fa26e4ebc06` |
| `1.11--1.11.1` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.11--1.11.1.md` | `74948d322c7f1d127a79f032cd8aabc56705409a50de3bcae7f886ab82746190` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.11--1.11.1-direct-turn.md` | `4dfac8c6d7d0c387b5929282d593c415b6c78987ace47cf4ef264d61fcc0bd72` |
| `1.11.1--1.11.2` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.11.1--1.11.2.md` | `b69bae4d471603590ea2f1df1a94439214a8d489992af5fe8c70743fed4060ec` | `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-source/1.11.1--1.11.2-direct-turn.md` | `f44697f276692658d57a68e449b00c193ce0fd5d7d6de9fadeadd6937c6f641f` |

## Retraced result

Across all seven source versions, the exact extracted method-body hashes match: `Entity.setRotation` = `9914a6040906c2b1119c6382b21ee2e3a932dd0f16d2af08b8d6bb047bfa3930`; `Entity.updateLocalPlayerCamera` = `8d958b4c344d3230a2e1b967c7d1785426e38d4366af687d27cfee58d9fc8455`; `Entity.updateVelocity` = `dc69b7e35a30fb51d2ac87bf546fa960e98a246c76731162ba5cd68b9cf98a4e`. The float yaw/pitch/lastYaw/lastPitch declaration order, `% 360.0F` setter, tick copy `lastYaw = yaw`, wrap boundaries `< -180.0F` and `>= 180.0F`, and operation order agree.

The ordinary dry branch is behind the local/logical movement gate and the water/lava exclusions, with fall-flying handled separately. In the non-fall-flying branch it selects `u = getSpeed() * (0.16277136F / (s * s * s))` on ground or `u = speedInAir` in air, then calls `updateVelocity(sideways, forwards, u)`. The consumer applies matching yaw sine/cosine signs to X/Z velocity. No difference was found in the reviewed slice.

## Exact open dependency and evidence note

The upstream production of `sideways`/`forwards` and the selected ground/air scale (including input normalization, speed/effects, and ground friction) remain outside this bounded consumer audit. Other `moveRelative` branches and full travel parity are also unreviewed.

The stored pair drafts describe provenance as unavailable for 1.10 and 1.10.1. The retained source-freeze pins provenance files for those releases, and the audited local files match those hashes; therefore that availability statement is stale for the pinned provenance files. The source-list is not pinned in the retained freeze for those two releases, so its status remains distinct and is not upgraded here.

## Verified evidence digest

SHA-256 over the UTF-8/LF canonical sequence `commit=<commit>`, `freeze=<path> <raw-sha>`, then the twelve Markdown `<path> <raw-sha>` records in pair/sidecar order above:

`728323060bba18f0ec021d1228614313be84486bb34826a3f2dcb4926c1e2bc0`