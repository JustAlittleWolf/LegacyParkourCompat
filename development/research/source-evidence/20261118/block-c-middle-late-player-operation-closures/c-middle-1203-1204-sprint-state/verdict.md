# Independent audit: 1.20.3 → 1.20.4 sprint-state slice

## Bound input and verdict

Audited the immutable source report at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-middle-1203-1204-sprint-state\report.md`, SHA-256 `29bbfc038e49467298a5d040a1d6e8377dc056eed2bf9652c7832ee3ddc4ccf` (7,516 bytes). This verdict applies to those exact report bytes and the exact source evidence below.

**Verdict: ACCEPT the bounded no-difference claim for ordinary local on-foot sprint-state input sampling, start/timer/stop gates, and sprint-flag writes.** I found no error in the report's scope, compared slices, or described operation order. This does not accept pair-wide coverage or freeze the 1.20.3–1.20.4 pair.

## Source identity audit

The environment is Windows PowerShell. The 1.20.3 Mojmap tree is `decompiled_minecraft/1.20.3/mojmap/`; its `.decompile-provenance.json` SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. The embedded version metadata names exact release 1.20.3; its source manifest enumerates 5,048 Java files. All 5,048 current files match their listed digests, with no mismatch or missing entry.

The 1.20.4 Mojmap source files are currently under `decompiled_minecraft/1.20.4/mojmap/`; adjacent source metadata is `decompiled_minecraft/1.20.4/mojmap.provenance.json` (SHA-256 `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`), `mojmap.ready.json` (`6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`), and `mojmap.sources.sha256` (`fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`). The ready marker names version 1.20.4 and `mojmap`; its source-manifest and artifact-manifest digests match the adjacent manifests. All 5,048 current source files match the 5,048-entry source manifest, with no mismatch or missing entry.

The 1.20.4 provenance records an earlier staging root at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.20.1-to-1.20.6-3e08bcae66a145b296f7f85b12623999` and a ready output path at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.20.4\mojmap`; those two recorded paths are no longer present. The current canonical source tree nevertheless matches the adjacent 1.20.4 source manifest byte-for-byte. I therefore bind this audit to the present files and their matching source-manifest identity; I do not claim that the original decompilation ran at the present canonical path.

## Independent slice review

The following files are byte-identical across both exact source trees; each listed SHA-256 was recalculated on both sides and matches:

| Source file | SHA-256, both releases |
|---|---|
| `net/minecraft/client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/client/Options.java` | `39ae95ee0def943be3f9ada31c65b5c8a8a258d61fcad09701903bfd93c3f4fa` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |

The independently inspected code is at those same paths in both `decompiled_minecraft/1.20.3/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`:

- `KeyboardInput.java:12-34` samples up/down/left/right, computes signed float impulses, then samples jump/shift and optionally scales movement. `Input.java:18-24` returns `(leftImpulse, forwardImpulse)` and uses the strict `forwardImpulse > 1.0E-5F` forward predicate. `Options.java:459` declares sprint as a `ToggleKeyMapping`; sprint is queried directly by `LocalPlayer`, not added to `Input` by keyboard sampling.
- `LocalPlayer.java:647-648,655-657,664,686-715` decrements a positive trigger timer before taking old jump/shift/impulse snapshots; samples input afterward; then the old shift snapshot can clear the timer before start checks. The double-tap path uses old shift/impulse snapshots, current ground-or-underwater state and the post-sample start gate; it arms the timer to `7` when expired and the sprint key is up, otherwise writes sprint true. Held-sprint start is a separate key/state gate. The stop path first checks current forward impulse and food, then horizontal-collision and water predicates, with a separate swimming branch. The code order and branches match the report.
- `LocalPlayer.java:1013-1034` confirms the actual start predicate: not already sprinting; sufficient impulse; the food/passenger gate; not using an item; no blindness; permitted vehicle control; and not fall-flying. For on-foot movement, food is above 6 or the player may fly. The impulse threshold is `>= 0.8` except underwater, where `Input.hasForwardImpulse()` is used.
- `Entity.java:2155-2160,2229-2240` confirms sprint state reads shared flag bit 3 and sets/clears it in the shared flags byte.

## Limits

Mounted behavior, active item-use slowdown mechanics, sprint-jump behavior, food production, geometry/collision and fluid predicate producers, and their transitive dependencies were not audited. They remain outside this bounded verdict and open for pair coverage. The report correctly treats its result as a slice-level no-difference finding rather than a full-pair completion.
