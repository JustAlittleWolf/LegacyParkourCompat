# WORLD-02: Boat passenger position and orientation writes change

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java`, `public void updateRiderPositon() {`, lines 366-372.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java`, `public void updateRiderPositon(Entity rider) {`, lines 558-586.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java`, `protected void copyEntityData(Entity rider) {`, lines 588-595.

Difference: World dispatches a mounted entity to `rideTick()` in both versions. 1.8.9's base method ticks the rider, then calls the boat's no-argument position update.

The 1.8.9 boat sets rider position to `x + cos(yaw) * 0.4`, `y + mountHeight + rideHeight`, and `z + sin(yaw) * 0.4`; it does not write rider orientation. 1.8.9's base `rideTick()` accumulates and reduces wrapped yaw/pitch deltas but does not assign rider yaw, pitch, or head yaw, and the source-tree reference search found no other consumers of those accumulators.

1.9.4's base method ticks the rider, then calls `updateRiderPositon(this)`.

With one passenger, the 1.9.4 boat's horizontal offset `f` remains zero, so it places the rider at the boat center.

The method then adds the boat's `yawVelocity` to rider yaw and head yaw and calls `copyEntityData`, which sets body yaw to boat yaw and clamps the wrapped rider-to-boat yaw difference to `[-105, 105]` degrees while adjusting `lastYaw` and head yaw.

Movement meaning: The local mounted-player path is `World.tickEntities()` -> `Entity.rideTick()` -> virtual `this.tick()` -> player/living tick -> the vehicle-specific passenger update after the rider tick.

LocalPlayer/rideable dispatch and the boat override are identified in both source trees.

The exact local tick uses and boat input transfer are documented in the run's TICK-04 row.

Boat velocity/trajectory inputs are excluded; only the boat's direct player position/orientation writes are claimed here.

Source proves that the callback writes different passenger coordinates for a one-passenger boat and that 1.9.4 directly writes rider yaw/head yaw while 1.8.9 does not.

Multiple-passenger seating and removed-boat handling have additional 1.9.4 branches and are not generalized by this one-passenger comparison.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-02: Boat passenger position and orientation writes change; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java",
      "member": "public void updateRiderPositon() {",
      "lines": "366-372",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 577d984f5653824fcff2300c29a0117426bf1a05322170e563b2dcac6a335e57."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java",
      "member": "BoatEntity#copyEntityData",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java",
      "member": "public void updateRiderPositon(Entity rider) {",
      "lines": "558-586",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 cc19a0c490b9df0d6606521629985424e10366db19ce2b57132a9741b76c7268."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java",
      "member": "protected void copyEntityData(Entity rider) {",
      "lines": "588-595",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7b8257ef577952401ed1da572d8cd184b22b7f2412a5b15368bab2abf6fbff0b."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-02-boat-rider-transform is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-02-boat-rider-transform",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-02-boat-rider-transform",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
