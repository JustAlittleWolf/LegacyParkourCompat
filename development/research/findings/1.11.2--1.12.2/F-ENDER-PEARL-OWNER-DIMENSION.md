# F-ENDER-PEARL-OWNER-DIMENSION: 1.12.2 clears a pearl's player thrower before dimension transfer

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `protected void onCollision(HitResult result) {`, lines 37-98.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `public Entity changeDimension(int dimension) {`, lines 119-125.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java`, `protected void onCollision(HitResult result) {`, lines 40-105.

Difference: The generic server portal tick reaches the virtual `changeDimension` method for a pearl that has entered a Nether portal and completed its portal timer.

On 1.11.2, the inherited transfer serializes the live player thrower into `ownerName` before recreating the pearl, so a matching player in the destination world can be resolved as its thrower afterward.

On 1.12.2, the pearl override first clears the live thrower when the target dimension differs; for a newly thrown pearl whose `throwerName` has not already been populated, the inherited serializer therefore writes an empty owner name and the replacement pearl cannot resolve that player.

When the transferred pearl later collides, both versions use the same player response guard: connected server player, player world equal to pearl world and player not sleeping.

If that guard holds, 1.11.2 can teleport the player to the pearl and 1.12.2 has no thrower to enter the branch.

If it does not hold, neither version teleports that player through the ordinary pearl collision branch. 1.11.2 previously populated owner name may remain serialized in 1.12.2 because the override clears `thrower`, not `throwerName`; the finding therefore states the fresh-pearl precondition rather than claiming all dimension transfers lose ownership.

Movement meaning: Source establishes a difference in player thrower retention across a pearl dimension transfer, and a reachable conditional difference in the later player position write.

It does not establish how often players encounter that sequence or a measured trajectory.

The 1.12.2-only End Gateway advancement callback does not itself write movement state.

Entity dimension transfer, packet authority and other external movement writers remain in S7.3/S7.4; no damage behavior is in scope.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-ENDER-PEARL-OWNER-DIMENSION: 1.12.2 clears a pearl's player thrower before dimension transfer; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java",
      "member": "EnderPearlEntity#changeDimension",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 290f7923d6467e252d2c96dd0f6939c37eef3597a85faeb022fd4be8bb3b7714 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java",
      "member": "protected void onCollision(HitResult result) {",
      "lines": "37-98",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 290f7923d6467e252d2c96dd0f6939c37eef3597a85faeb022fd4be8bb3b7714; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 1e7fd38169975458ab8d28fed4cbad2b43ed88469009fb12f8782903d6740dce."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java",
      "member": "public Entity changeDimension(int dimension) {",
      "lines": "119-125",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ef91e2979a5027f5d28a40af9b809bc312bccb19cb2016b6d20f5c145cdd0e35; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e2803ed1f81db6698d150d60aa70b6a6479dbf43875fb9b5ef4a90b7e88ec01c."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/projectile/EnderPearlEntity.java",
      "member": "protected void onCollision(HitResult result) {",
      "lines": "40-105",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ef91e2979a5027f5d28a40af9b809bc312bccb19cb2016b6d20f5c145cdd0e35; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e2f543954656af5109a36bf60a02f6cac95cb17fbe68ccfedc28e184d4232985."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-ENDER-PEARL-OWNER-DIMENSION is unknown within (1.11.2, 1.12.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-ENDER-PEARL-OWNER-DIMENSION",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-ENDER-PEARL-OWNER-DIMENSION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
