# F022: Contextual fluid height in the local submerged-water cache

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `m_61708540, isSubmergedIn`, lines 979–981, 1042–1051.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `isSubmergedIn`, lines 1035–1052.

Difference: 1.13.2 `Entity.isSubmergedIn` tests the eye-cell fluid tag and compares the eye Y against `blockY + (fluidState.getHeight() + 0.11111111F)`. 1.13.2's `FlowingFluid.getHeight(FluidState)` is `state.getLevel() / 9.0F` and has no world context. 1.14.4's `m_69330161(tag, boolean)` applies the same eye-cell test, optionally checks that the chunk is loaded, and calls `FluidState.getHeight(world,pos)`. 1.14.4's `FlowingFluid.getHeight` returns `1.0F` when the eye-cell fluid and the fluid above are accepted by the eye-cell fluid's `Fluid.is` predicate; otherwise it delegates to the same level/9 calculation. This helper feeds two distinct caches: Entity's base-tick `submergedInWater` field (through `m_61708540`, with `true` in 1.14.4) and PlayerEntity's separate `isSubmergedInWater` field (through `updateWaterSubmersion`, with `true` in 1.14.4; 1.13.2 directly calls `isSubmergedIn`). The source-visible conditional predicate difference is therefore: for an eye-cell state with level below 8, an above fluid accepted by `Fluid.is`, and an eye fraction greater than 1.13.2's `level/9.0F + 0.11111111F` but below 1, 1.14.4 can return true where 1.13.2 returns false. 1.14.4's extra chunk guard cannot reject the ordinary local-player path: `LocalClientPlayerEntity.tick` checks that the chunk at the player's X/Z is loaded before calling the superclass tick, and the eye query has the same X/Z.

Movement meaning: `PlayerEntity.tick` refreshes its own `isSubmergedInWater` cache before `super.tick()` in both versions; 1.13.2 has an extra bubble-column sound-state helper call immediately before this refresh, which remains outside this slice.



## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F022: Contextual fluid height in the local submerged-water cache. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "m_61708540, isSubmergedIn",
      "lines": [
        [
          979,
          981
        ],
        [
          1042,
          1051
        ]
      ],
      "status": "verified-content",
      "sha256": "1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "isSubmergedIn",
      "lines": [
        [
          1035,
          1052
        ]
      ],
      "status": "verified-content",
      "sha256": "7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/fluid/FlowingFluid.java",
      "member": "m_50081546, getHeight",
      "lines": [
        [
          442,
          448
        ]
      ],
      "status": "verified-content",
      "sha256": "6afb033d18e6d28ff6d7deda88f5f1432914903b0fef100c44fb8372d3e148ce"
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/fluid/WaterFluid.java",
      "member": "is",
      "lines": [
        [
          98,
          99
        ]
      ],
      "status": "verified-content",
      "sha256": "ed5a28c7f4370ef8f7217dae482fbb1e28296f08c1d70944a1a690bf5f6bb13f"
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.",
      "next_action": "Retain existing Feather-r1 provenance caveat; original equivalence requires the exact preserved artifact, not regeneration during preparation."
    },
    {
      "missing": "Physical player fit, producer/partial-chunk admission and cache timing are not closed; revised claim has no independent acceptance or exact release boundary.",
      "next_action": "Retain candidate/no handoff; exact revised snapshot is development/research/archive/branch-integration-2026-10-13/dfcf8fbb0b057f105415aa65bfed0748378128046485e47739623326f615bdc6.md"
    }
  ],
  "runtime": "unverified"
}
```
