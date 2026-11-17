# 26.1.2 → 26.2: selected block shape providers

## Scope

This is a bounded comparison of direct collision/outline/support shape and state-selection providers for `SnowLayerBlock`, `CarpetBlock`, `ScaffoldingBlock`, `SlimeBlock`, and `HoneyBlock`, together with their directly relevant `Blocks` registrations. It does not establish ordinary-world reachability, registry/data membership, pair-wide coverage, or runtime parity. Block-contact callbacks, restitution, friction/speed/jump-factor consequences, unrelated block providers, and inherited collision dispatch are outside the assignment.

## Exact source identity

Both source trees are published as `unobfuscated` and live in `decompiled_minecraft/<version>/unobfuscated/`. Provenance/ready/source/artifact sidecars are at the version root.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Declared identity |
| --- | --- | --- | --- | --- | --- |
| 26.1.2 | `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7` | `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` | `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0` | `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92` | ready/provenance versionId `26.1.2`; mapping/namespace `unobfuscated`; source count 6,882 |
| 26.2 | `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F` | `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1` | `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894` | `BA9DC53A41BCA9744EC1C8AB2CE9363764A22333C39EF64D5105F4E3BF5C98BA` | ready/provenance versionId `26.2`; mapping/namespace `unobfuscated`; source count 7,055 |

Ready markers declare matching source and artifact manifest digests. Local SHA-256 values for every cited Java file were checked against its endpoint source-manifest row.

## Provider findings

- **Snow layers:** `SnowLayerBlock.java` is byte-identical at both endpoints. Its default state uses `LAYERS=1` (line 38); outline and support shapes index `SHAPES[LAYERS]`, while collision indexes `SHAPES[LAYERS-1]` (lines 47–59). The shape table is built from eight columns with height `height*2` (line 28). `canSurvive` and `updateShape` retain the same support-tag/full-upper-face/eight-layer gate (lines 77–102). The predicate is unchanged; membership of `CANNOT_SUPPORT_SNOW_LAYER` and `SUPPORT_OVERRIDE_SNOW_LAYER` is not checked here.
- **Carpet:** `CarpetBlock.java` and `WoolCarpetBlock.java` are byte-identical. `CarpetBlock` uses one fixed one-sixteenth-high column and `getShape` returns it (lines 17, 29–31); survival remains `!level.isEmptyBlock(pos.below())` (lines 50–52). `WoolCarpetBlock` extends `CarpetBlock` at both endpoints. `MOSS_CARPET` directly registers `CarpetBlock::new` at 26.1.2 `Blocks.java:6747–6750` and 26.2 `:5539–5542`; its listed strength/sound/push properties match. The colored carpet registration representation differs (individual wool-carpet registers versus `ColorCollection` in 26.2), but both use `WoolCarpetBlock` with the same provider inheritance. This does not cover the separate `MossyCarpetBlock` provider used by pale moss carpet.
- **Scaffolding:** `ScaffoldingBlock.java` is byte-identical. Default state remains `DISTANCE=7`, `WATERLOGGED=false`, `BOTTOM=false` (line 52). Outline shape retains the held-item/BOTTOM selector (lines 61–67). Collision retains placement-empty, above-and-not-descending stable-shape, and distance/BOTTOM/above-shape branches (lines 140–150). `getDistance` continues to use a sturdy lower face or nearby scaffolding distance (lines 161–175). The `SCAFFOLDING` registration keeps its constructor and relevant no-collision/dynamic-shape/push-reaction properties; only its registry key representation changes to `BlockItemIds.SCAFFOLDING`.
- **Honey:** `HoneyBlock.java` differs elsewhere, but the selected `SHAPE` declaration and `getCollisionShape` body match: a constant `Block.column(14.0, 0.0, 15.0)` returned at lines 31, 47–49 in both. The `HONEY_BLOCK` registration retains `.speedFactor(0.4F).jumpFactor(0.5F).noOcclusion()` at 26.1.2 `Blocks.java:5928–5932` and 26.2 `:4963–4967`; constructor/properties are the same, with registry key representation changed. Other HoneyBlock contact/slide methods are outside this shape-only review.
- **Slime:** `SlimeBlock` declares no direct shape-provider override in the inspected source; its file differs across endpoints. The `SLIME_BLOCK` registration preserves `.friction(0.8F)` and `.noOcclusion()`, but 26.2 adds `.bounceRestitution(1.0F)` (26.1.2 `Blocks.java:3221–3222`; 26.2 `:2976–2979`). That movement-related registration delta and the changed contact/bounce implementation are explicitly unexamined by this shape-provider report and need their own source operation review; no restitution behavior conclusion is made here.

No difference was identified within the assigned direct shape/state provider slice for snow layers, carpets, scaffolding, or honey collision geometry. The Slime registration lead is not accepted as a finding by this report.

## Explicit limits

No resource/tag membership was inspected for snow support. No world/data state reachability, `MossyCarpetBlock` geometry, Slime restitution/contact behavior, Honey contact/slide behavior, or complete registered provider inventory was covered. This report is not a full-pair terminal inventory or independent coverage audit.

## Hash-bound Java files

Relative paths are under each endpoint's `unobfuscated/` source root. Each local hash below matched the corresponding `unobfuscated.sources.sha256` entry.

| File | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `312952c724788f30a2255bc34ef7d3242d4737067c23939f0bd0a76059b473cc` | `312952c724788f30a2255bc34ef7d3242d4737067c23939f0bd0a76059b473cc` |
| `net/minecraft/world/level/block/CarpetBlock.java` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` | `ce669310bf7ab918e5bbdb07253b9030a516ae143cd4aa9e464bd7f2fdedffdc` |
| `net/minecraft/world/level/block/WoolCarpetBlock.java` | `f99bd32dc81aac89584803bc9eb92ee229502cbb5719d726672195d879365b4e` | `f99bd32dc81aac89584803bc9eb92ee229502cbb5719d726672195d879365b4e` |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `a55b102f47b261f808ef31c0b74cda9efdbc6fa53ccc4bda8ce46230904b22fa` | `a55b102f47b261f808ef31c0b74cda9efdbc6fa53ccc4bda8ce46230904b22fa` |
| `net/minecraft/world/level/block/SlimeBlock.java` | `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e` | `e0fc3087b66777a2800676aaeee47a6e98395ea4c964d5a301f3a73b3577c754` |
| `net/minecraft/world/level/block/HoneyBlock.java` | `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec` | `ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa` |
| `net/minecraft/world/level/block/Blocks.java` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |


