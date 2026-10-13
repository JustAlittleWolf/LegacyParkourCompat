# TICK-07: client-side living pushes can write local-player velocity

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `protected void pushAwayCollidingEntities() {`, lines 1463-1476.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `protected void pushAwayCollidingEntities() {`, lines 1742-1750.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/EntityFilter.java`, `public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {`, lines 56-94.

Difference: 1.8.9 runs the living entity post-travel push query only on the server. 1.9.4 runs it on client and server. 1.9.4's client filter rejects ordinary remote/non-player candidates but can select the locally controlled player from another living entity's query. `pushAway` forwards as `candidate.push(this)`.

The `Entity#push` body applies opposite horizontal velocity deltas to both participants, subject to same-vehicle/no-clip/passenger and minimum-distance guards.

Therefore, when a client-side living entity's query includes the local player, 1.9.4 has a reachable client-side write to local-player horizontal velocity; 1.8.9 has no corresponding client-side call.

The query also changes from an X/Z-expanded current box (`grown(0.2F, 0.0, 0.2)`) with `NOT_SPECTATOR && isPushable()` to the current box with `EntityFilter.canBePushedBy(this)`.

Those filters are not equivalent; the source establishes the changed query and filter boundaries, not which candidates occur in any particular world state.

Movement meaning: For this route, a non-local living entity is the query owner, while the local player is the selected candidate.

Required guards include overlap with that exact query box, local player pushability/non-spectator status, allowed team collision rules, not sharing a vehicle, neither participant no-clip, the relevant passenger checks, and horizontal separation reaching the method's `0.01F` cutoff.

The local player's own client query is not the path: its filter does not admit ordinary remote entities as candidates.

This distinction closes the producer-to-player-writer direction without attributing independent entity movement as an endpoint.

The paired source proves an additional client-side route that can alter local-player horizontal velocity after the local player's movement, when the listed collision and filter preconditions hold.

It does not establish a particular trajectory or compare server-authoritative correction outcomes.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements TICK-07: client-side living pushes can write local-player velocity; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "protected void pushAwayCollidingEntities() {",
      "lines": "1463-1476",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 1fda4d12c4d253b3212b04ce1c41c152a71bba5d20a7b9e91f22c8e515eecbab."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/EntityFilter.java",
      "member": "EntityFilter#canBePushedBy",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d97be58e0b8e965e06a0b989685f156be517d89d3b4d0f3154adf7bd3612b6b9 (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "protected void pushAwayCollidingEntities() {",
      "lines": "1742-1750",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 41f3e804ae01cdd1725531d7c470bd65ae0cc0c0d9375c8b2106870acd70532a."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/EntityFilter.java",
      "member": "public static <T extends Entity> Predicate<T> canBePushedBy(Entity entity) {",
      "lines": "56-94",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8a3eaf42852fc9db119a3d09e67c011d498211f5e13bdfbee26d0ca14f76e429."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-TICK-07-client-player-push is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-TICK-07-client-player-push",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for TICK-07-client-player-push",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
