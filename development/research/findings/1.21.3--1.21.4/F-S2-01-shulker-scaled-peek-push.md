# F-S2-01: 1.21.4 centers the scaled Shulker peek-push query

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, getScale 541-544.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, getScale 541-544.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/monster/Shulker.java`, tick 170-178.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/monster/Shulker.java`, tick 170-178.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleUpdateAttributes 2156-2180.

Difference: In 1.21.3, `Shulker.onPeekAmountChange()` queries `getProgressDeltaAabb(...)` and translates that box by `(getX() - 0.5, getY(), getZ() - 0.5)`. The helper begins with local bounds `[0, scale]` on X, Y and Z, then expands and contracts them according to the attachment face and previous/current peek values. In 1.21.4, the call passes `this.position()` to an overload that begins with centered X/Z bounds `[-scale/2, scale/2]`, applies the same expansion and contraction expressions, and translates the result by the entity position. For an upward-attached Shulker at position `(x, y, z)` with scale `2.0F`, the UP direction leaves the query's X/Z intervals unchanged by expansion and contraction. 1.21.3 queries X and Z over `[x - 0.5, x + 1.5]`; 1.21.4 queries over `[x - 1.0, x + 1.0]`. These regions differ by a half block on each side. 1.21.3 player in the 1.21.3-only fringe is selected and moved upward in 1.21.3, but is not selected by this query in 1.21.4. The direct displacement remains `peekDelta * scale` along the face-opposite direction when the player is selected.

Movement meaning: `Attributes.SCALE` is a syncable living-entity attribute with default `1.0` and range `0.0625` to `16.0`; `2.0F` is within that range. Both clients' `handleUpdateAttributes()` resolve the target living entity and apply the packet's base value and transient modifiers to its attribute instance. The Shulker type's registered attributes include the inherited LivingEntity attributes, so the scale read by `getScale()` can differ from the default on the client. The paired Shulker tick calls `updatePeekAmount()` and then `onPeekAmountChange()` when the amount changes. During an opening step, the method searches entities in the version-specific query region, excludes spectators and passengers of the same vehicle, then directly calls `Entity.move(MoverType.SHULKER, displacement)` on every non-Shulker entity with physics enabled. The local player is therefore a reachable movement consumer when it occupies the differing fringe. This finding is limited to the direct player-push query and the stated non-default-scale/opening conditions.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/monster/Shulker.java",
      "member": "tick",
      "lines": "170-178",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4103fb6f4ee4f4615ae4bcf4094f14f61821c2c715aaf5d2b2721390c41e4de; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/monster/Shulker.java",
      "member": "tick",
      "lines": "170-178",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 6da89fa5d298c1913ed832aacc6fa19591a13b4be5586d3dbe5085f33457a5d2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleUpdateAttributes",
      "lines": "2156-2180",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getScale",
      "lines": "541-544",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getScale",
      "lines": "541-544",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
