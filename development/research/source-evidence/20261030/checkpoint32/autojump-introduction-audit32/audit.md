# Independent source audit: auto-jump introduction

## Verdict

**ACCEPT**, bounded to source absence in 1.9.4 and source presence/behavior in 1.10, 1.10.1 and 1.10.2. The source-corrected finding is `local-auto-jump.md` in this directory. This establishes 1.10 as the first confirmed inspected release, not a patch-exact introduction date across all 1.9.x versions.

## Evidence checked independently

Canonical read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, `ornithe-feather` (Ornithe Feather Gen2). The four relevant files in each release match the full source-hash manifest (1.9.4 and 1.10.2) or embedded full per-file source-hash map (1.10 and 1.10.1). Manifest/provenance SHA-256 identities agree with the supplied source dossier: 1.9.4 manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; 1.10 provenance `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; 1.10.1 provenance `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; 1.10.2 manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.

Relevant source SHA-256s (all match pins):

| Release | LocalClientPlayerEntity | Input | GameOptions | ControlsOptionsScreen |
|---|---|---|---|---|
| 1.9.4 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009` | `1bcddb6d5c8aa3f32119a11c35a9c351b4bd6bf36aefc7aa0599c0ab329ad41e` | `a3b878360e97a06e44cc352bb6a7f23afa86a3d7dc4aea594e65b4ae212fc5b2` |
| 1.10 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `035c70cb9479e5943e9d1d232bcdf5eb823b4f2e1f7107e6b2fad68b9a7c62c5` | `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |
| 1.10.1 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `8e34d181f4b66f79fcf2ad477832edb08ca1ff6099f507fdfd97d1329da3178e` | `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |
| 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43` | `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` |

The 1.9.4 whole-tree alias scan returned no matches. All four later source files were checked against their pins; identical local player and input hashes across 1.10–1.10.2 independently establish identical detector and input-accessor bodies. The option source hash varies, so its auto-jump-specific lines were compared: default true, option toggle/read/load/save, and option registration persist; unrelated surrounding options account for source-wide file hash changes. Exact source places cache copy under `isCamera()` and applies Jump Boost to the height ceiling. The detector is dispatched after `super.move`; countdown injection occurs before flight-toggle handling and is suppressed there by `bl4`.

## Minimal-operation/dependency judgment

The pre-introduction behavior can be represented simply by the absence of any auto-jump queue and press path. That negative operation does **not** require collision dependencies. Do not wait for the post-introduction detector's collision scan to accept this source absence/presence finding. The post-introduction feature can be decomposed into setting/cache eligibility, post-move detect/enqueue, and queued press consumption; only the scan within detection requires collision-shape/world-query closure. Pure eligibility gates are independently addressable: the inspected 26.2 `LocalPlayer.canAutoJump()` at lines 1121–1127 returns gates on option, timer, ground, surface, passenger, movement and block jump factor before `updateAutoJump` performs its geometry scan. This current contract is illustrative only; it is not evidence for 1.9.4/1.10 historical behavior.

No wiki, backlog, product code/history, tests, runtime or decompilation was consulted/performed.
