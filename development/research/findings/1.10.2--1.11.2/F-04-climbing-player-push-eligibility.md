# F-04: Climbing players are excluded from the push recipient filter

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isPushable() {`, lines 1887-1889.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 56-94.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isPushable() {`, lines 1950-1952.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 50-88.

Difference: For a non-removed living player, 1.10.2 reports pushable regardless of climbing. 1.11.2 rejects the player while climbing.

The paired filter excludes rejected entities from the collision candidate list.

On the client, the filter permits a player candidate only when it is local; therefore a remote player can select the local player when its bounding box overlaps the local player's box and collision rules permit the interaction.

Its inherited `pushAway` calls `entity.push(this)` on that local player.

The `Entity.push` path can add horizontal velocity when entities are not in the same vehicle, neither is no-clip, horizontal separation is at least `0.01F`, and the target has no passengers.

Movement meaning: Remote player tick -> inherited `LivingEntity.pushAwayCollidingEntities` -> `EntityFilter.canBePushedBy(remotePlayer)` -> local player `isPushable` -> `pushAway`/`Entity.push` -> local player's velocity.

The direct precondition for the difference is that the local player is climbing and the remote player's collision query otherwise admits the local player.

Team collision rules, overlap geometry and `Entity.push` guards gate the call.

This finding does not model entity movement or dead-player behavior.

Source proves 1.11.2 can omit a climbing local player from this push recipient list where 1.10.2 would include it, preventing the downstream local-player velocity write on this path.

The magnitude and resulting trajectory are not claimed.

The separate `isAlive()` gate can also affect removed/dead entities, but those state producers are excluded and are not part of this finding.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-04: Climbing players are excluded from the push recipient filter; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public boolean isPushable() {",
      "lines": "1887-1889",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4a2b82828526de9ebba2bd4613d37bc285d442519e12bf9516e808b99b3dcb3b."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/entity/EntityFilter.java",
      "member": "public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {",
      "lines": "56-94",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8a3eaf42852fc9db119a3d09e67c011d498211f5e13bdfbee26d0ca14f76e429."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public boolean isPushable() {",
      "lines": "1950-1952",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c0786c936df0d8cf2acbd093d6c224176973f07933fd9b5fa9e5c78a0f62b984."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/entity/EntityFilter.java",
      "member": "public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {",
      "lines": "50-88",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8a3eaf42852fc9db119a3d09e67c011d498211f5e13bdfbee26d0ca14f76e429."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-04-climbing-player-push-eligibility is unknown within (1.10.2, 1.11.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-04-climbing-player-push-eligibility",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-04-climbing-player-push-eligibility",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
