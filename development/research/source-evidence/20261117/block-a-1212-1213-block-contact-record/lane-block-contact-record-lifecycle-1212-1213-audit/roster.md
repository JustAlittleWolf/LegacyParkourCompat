# Frozen source roster: 1.21.2 → 1.21.3 block-contact record lifecycle

Created before inspecting the scoped method bodies. The exact-version Mojmap source trees are read-only. This roster was frozen before behavior comparison; the four paired classes cover the scoped movement methods and inherited post-travel caller.

## Exact publication identity

- **1.21.2, Mojmap:** `decompiled_minecraft/1.21.2/mojmap/`; provenance SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. Its pinned version metadata is `1.21.2.json` (URL package ID `c14db17c663edf3d7a698488263a21b1df6d02af`, pinned content SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`) and official mapping input is `client.txt` SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.
- **1.21.3, Mojmap:** ready publication `decompiled_minecraft/1.21.3/mojmap`, version metadata `decompiled_minecraft/1.21.3/mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`; ready marker SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; source manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. The publication metadata names exact version `1.21.3`, namespace `mojmap`, and mapping option `mojmap`.

## Frozen source classes and SHA-256

| Relative class path | 1.21.2 SHA-256 | 1.21.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |

## Frozen slice

- Environmental player movement-record creation or absence in `Entity.move` and `LocalPlayer.move`.
- `oldPosition()` → current `position` no-argument fallback and its post-travel caller.
- Ordinary versus piston movement record behavior, scan order and movement-list copy/clear behavior.
- Teleport only where a direct producer or invalidator exists in the frozen classes.
- Actual block callback traversal, reported separately from solid collision geometry and server/non-player paths.
## Supplemental roster frozen before claim-followup inspection

After the candidate comparison identified the direct piston helper, traversal implementation, and server-only append call sites, these exact version-root source pairs were added to verify those bounded claims. Their bodies were not read before the paired hashes were captured. They are used only for the no-argument piston callback, block traversal implementation, and caller-context/exclusion checks.

| Relative class path | 1.21.2 SHA-256 | 1.21.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` |
| `net/minecraft/world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |