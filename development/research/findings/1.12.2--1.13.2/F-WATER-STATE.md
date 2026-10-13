# F-WATER-STATE — 1.13.2 adds submerged-depth and swimming state to player movement

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 948-964.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateSwimming() {`, lines 953-959.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean isSubmergedInWater() {`, lines 943-945.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `private void m_03231680() {`, lines 947-951.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean m_69693160(Tag<Fluid> tag) {`, lines 2509-2571.

Difference: 1.13.2 adds a sampled fluid depth and a swimming flag transition.

Already-swimming transition uses sprint + contact with water + not riding; entry uses sprint + submerged-in-water + not riding.

PlayerEntity suppresses swimming while flying. 1.12.2 has no submerged-depth/swimming state.

Movement meaning: - Reachability and dependencies: 1.13.2 base-tick water update runs before LivingEntity.mobTick input/jump/travel route.

State is consumed by local sprint/flight gates, PlayerEntity pose/moveRelative, and LivingEntity water jump decisions.

Full Entity/Player tick order, fluid grid boundaries and resource-data closure remain pending.

Source difference does not establish exact gameplay trajectories.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/SwimmingState.java"
    ],
    "hooks": [
      "SwimmingHook"
    ],
    "limits": [
      "SwimmingState returns false via SwimmingHook. No dedicated current Change restores the old fluid-depth/contact sampler; the sampled depth and swimming-flag producer are distinct operations."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean checkWaterCollisions() {",
      "lines": "948-964",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 3e7ee90879d783b73ccca1552c5170f3d19b675894f8b7a835a6b4c6e8ee2e64."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#isSubmergedInWater",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_03231680",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#m_69693160",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void updateSwimming() {",
      "lines": "953-959",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 34d24e7283f22046f2361d660d57094403490e5ae549f921567be7ffda8b8170."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "Entity#checkWaterCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean isSubmergedInWater() {",
      "lines": "943-945",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 d73e1e620d4bbc1ca41d4d9c020c00d61f0ed1259bf458d0044da93ffa267353."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "private void m_03231680() {",
      "lines": "947-951",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 62eb055a426f7c53078564e48b79204d59c9b9bd082fde6246cb4c5e6ff37b31."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public boolean m_69693160(Tag<Fluid> tag) {",
      "lines": "2509-2571",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c354fc9dfd269c48bf3aedc76773d4a756608f639b2753f762b2613d9703c3ef."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void updateSwimming() {",
      "lines": "1471-1477",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 c909ed7792941016bfaf1c3fdbca726c98d5d14bfcf1669e829a1d90533b9dcf."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-WATER-STATE is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-WATER-STATE",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
