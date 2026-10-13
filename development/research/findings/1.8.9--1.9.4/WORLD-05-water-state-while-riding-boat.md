# WORLD-05: boat passenger skips player water collision update

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 746-760.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 849-865.

Difference: 1.8.9 always calls `World.applyLiquidDrag()` for the contracted entity box with `Material.WATER`.

If water is detected, it can reset `fallDistance`, set `inWater`, and clear `onFireTimer`; the World call can also add fluid-flow velocity. 1.9.4 first checks `this.getMount() instanceof BoatEntity`; in that case it sets `inWater` false and skips the entire water query and its state/velocity writes.

The check executes in the inherited `Entity.tick()` on both endpoints. 1.8.9 `World.applyLiquidDrag()` source is `World.java` lines 1486-1527 1.9.4 is lines 1560-1602

Movement meaning: The local player tick reaches inherited `Entity.tick()` through the paired player/living superclass chain; `Entity.tick()` calls `checkWaterCollisions()` at 1.8.9 line 300 and 1.9.4 line 361.

Under the concrete precondition that the player is mounted on a `BoatEntity` and its contracted box intersects water, 1.8.9 performs the water query and 1.9.4 skips it. 1.8.9's `World.applyLiquidDrag()` calls `Block.applyMaterialDrag()` for water cells; the paired `LiquidBlock` override contributes `getFlow()` to the accumulated vector.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_11/MountedBoatWaterInteraction.java"
    ],
    "hooks": [
      "FluidInteractionFluidHook"
    ],
    "limits": [
      "MountedBoatWaterInteraction substitutes EMPTY water fluid for a player riding a boat only when !boat.isUnderWater(). The 1.9.4 checkWaterCollisions excludes every boat passenger before sampling. No dedicated 1.8.9 restoration is registered, and this later underwater gate does not establish parity for this earlier unconditional passenger exclusion. SwimmingState changes swimming, not inWater."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean checkWaterCollisions() {",
      "lines": "746-760",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 2792ae073caa07110da5da6d686f805c78aa103c686c73a11762425d289f8627."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#isSubmergedInWater",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_03231680",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_69693160",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88 (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean checkWaterCollisions() {",
      "lines": "849-865",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 eeed94ba8a9986f99be6ea1892eb52c438c04280d0e7a3f4d759b26302477952."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#isSubmergedInWater",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_03231680",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_69693160",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85 (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean checkWaterCollisions() {",
      "lines": "746-760",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 2792ae073caa07110da5da6d686f805c78aa103c686c73a11762425d289f8627."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#isSubmergedInWater",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_03231680",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_69693160",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-05-water-state-while-riding-boat is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-05-water-state-while-riding-boat",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-09-boat-water-travel-branch",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
