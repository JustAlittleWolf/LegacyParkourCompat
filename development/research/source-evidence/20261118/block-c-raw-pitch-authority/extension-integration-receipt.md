# Product integration and build receipt: direct mouse-turn pitch saturation through 1.20.4

## Product identities and review

- Dedicated product branch: `codex/c-raw-pitch-saturation-117-1204`.
- Exact prior ready ancestry: `refs/campaign-ready/c-raw-pitch-turn-1211` at receipt commit `c0704c7b8a06b44d680ee9f48858cedc1a3e50ab`, tree `5688bbb647d8f1012bb1b506dfea779cd55c00ef`; prior code commit `9ec57a6256b39ea8a290b298322d827d8b3002de`, tree `d2d74eedc026224e8d01bdfc30e01c5ec7bae252`.
- Exact integration base and parent of the initial code commit: `c0704c7b8a06b44d680ee9f48858cedc1a3e50ab`.
- Code commits: `bc469fe6d6ae9e6b62ba5c16dcb13a3160209248`, followed by final source-citation correction `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce`.
- Reviewed and built code commit/tree: `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce` / `e49ce4e534203a675c5001b1ce8fa670f054db0a`.
- Independent product review: `c_raw_pitch_extension_product_review`, signed PASS for the exact code commit/tree; final verdict SHA-256 `1e932356609d5e10c0fe7dc2c8df9c4ccf5c374fea9839a5ac0207ccee8bc9f4`. The verdict's original bytes were preserved and the designated owner attested the exact-tree build.
- Candidate input: `dda78c5dc400fbf40c736324c330fd1a7cd7b93c` / `8958e57758d02f9d4ae3dec16d360028bc30805b`. It was reconciled with the existing operation; its duplicate hook, mixin, and provider classes are not in product ancestry.

## Accepted source evidence and applicability

- Accepted applicability report: `c-raw-pitch-earlier-range-acceptance/report.md`, SHA-256 `7fc31ac2995085e569be00658db0b1e68576233ca1f3e14fc345b85727b8cf07`.
- Signed source acceptance identities: A `ee30fad38ecac269c0c295df025cb97f227a446b`; C `9ef25ce2047fdeb52ea9b93e07c2e3a407800948`.
- C adjacent-source archive: bundle SHA-256 `21af32782e3f8099d8301e438b8a0bbf71021a30b29cc42556043bfd87eb21bd`; commit/tree `9ef25ce2047fdeb52ea9b93e07c2e3a407800948` / `4af21244582c38629a5171d2c3415840619d38be`. It binds thirteen adjacent direct-operation comparisons through 1.19.4.
- A 1.19.4→1.20.4 acceptance archive: bundle SHA-256 `13bfcd345aca74e8036088309fa720316c0e585a03d624135dd9f35e5d978614`; commit/tree `4e7eb98741c7e1e17c562d8021163229e0d3519c` / `9665dbb889a2cbede2399d011a029e0362e1a6ee`; five adjacent dispositions through 1.20.4. Its bundle verified with the five declared prerequisite refs.
- C 1.20.4→1.20.5 continuity/mouse-batching archive: bundle SHA-256 `494447147db3243749b830ce0c70242bad5bbe7bf35e0d787ce24ef622756017`; commit/tree `327c092ff111ed186bacdd9832c9c12e3df6348c` / `3dbaef77e18dca0557765215130e9fe9628e4711`. Report SHA-256 `45436c6764dc4abcf0ba333d80dc2110de686400944757fb73ca7681605120de`; independent verdict SHA-256 `a0dd152ebc9b70fda3b6d5860577c0bb34ad362810d56d1089cba8237d348120`. This accepts the pitch setter/clamp slice as unchanged and excludes the distinct callback-scheduling operation.
- The existing accepted 1.20.5–1.21.1 scope remains in the prior ready ancestry. Its source archive is bundle SHA-256 `95bbd3e86c34ccb719e6b8aae5d7b4277e9343f3d96490a803cc7209ff0e6349`, commit/tree `89bfa2d2a27ee67a969935b3e17101b344f34b67` / `99be874c0f954055e38b6e1b141f7ebd6574a384`; it also binds the 1.21.1→1.21.2 cutoff and separate 1.21.1→26.2 destination witness.

Exact source references in the Change comments are Mojmap 1.16.5 `Entity.turn(double,double)` lines 340–352; Mojmap 1.17 `Entity.turn(double,double)` lines 377–388 and `Entity.setXRot(float)` lines 3021–3027; Mojmap 1.20.4 `Entity.turn` lines 398–410 and `setXRot` lines 3309–3315; Mojmap 1.21.1 `Entity.turn` lines 401–413 and `setXRot` lines 3363–3369; Mojmap 1.21.2 `setXRot` lines 3489–3494; and exact 26.2 `Entity.turn` lines 489–501 and `Entity.setXRot` lines 3906–3916.

## Integrated operation and routing

The product retains its existing LocalPlayer-only first-setter transform, ordinal-0 mixin seam, and finite-only clamp. The integration moves the native identity fence from `V1_20_2` to `V1_16_2`, retaining the sole enabled `V1_21` clamp. It does not change pitch arithmetic, yaw, the later clamp, old-pitch accumulation, mixin/resource dispatch, or native fallback.

Only finite out-of-range first-setter values are mapped to `-90.0F` or `90.0F`. In-range values—including signed zero—and non-finite values pass unchanged. Non-finite values retain vanilla's rejection path.

| Selected profiles | Resolved provider | Result |
|---|---|---|
| Through `V1_16_2`, including `V1_16` and aliases through 1.16.5 | `V1_16_2` native fence | Native argument; no pre-1.17 saturation claim |
| `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2`, `V1_20_5`, `V1_21` | `V1_21` clamp | Historical finite pitch saturation; newly accepted scope begins at 1.17 and runs through 1.20.4, preserving prior 1.20.5–1.21.1 scope |
| `V1_21_2` and later historical profiles | No applicable provider | Native fallback |
| `CURRENT` | Resolver returns empty | Native behavior |

## Build disposition

Exact Windows build command on reviewed code commit `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce` and tree `e49ce4e534203a675c5001b1ce8fa670f054db0a`:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` in 9 seconds; 19 actionable tasks, 3 executed and 16 up-to-date. The init script disabled all Gradle `Test` tasks and `-x test` excluded the test task. No tests, game, TAS, Gym, server, Docker, or runtime launch was performed. No backlog or primary checkout was modified. Compilation does not establish runtime parity.
