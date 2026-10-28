# Citation correction supplement: post-travel callback method

This supplement corrects one method-name error in the source report below. It preserves the original report and the independent audit unchanged. The method-local ordering, guard conditions, and callback claims remain as reported; the callback sites are in `LivingEntity.aiStep()`, not `LivingEntity.tick()`.

## Bound artifacts

- Original source report: `.native-source/20261028/frontier-c/1214-1219-segment-recording-lifecycle/report.md`, SHA-256 `c4468295b043b2457e561c49d2c741d7c783f80a766ec303390cb6d889651db6`.
- Independent audit: `.native-source/20261028/frontier-c/1214-1219-segment-recording-lifecycle-audit/audit.md`, SHA-256 `cc9dea88864e9a62a552f9c04ad05f7f5c9c4eb6f1ddb7609861afa293cbf400`.

## Corrected source references

The callback at the cited line is inside `LivingEntity.aiStep()` in each exact Mojmap source:

| Release | Exact source path | `aiStep()` declaration | Post-travel `applyEffectsFromBlocks()` | Source SHA-256 |
| --- | --- | ---: | ---: | --- |
| 1.21.4 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.4\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2691 | 2786 | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| 1.21.5 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.5\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2669 | 2770 | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| 1.21.6 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.6\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2721 | 2822 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| 1.21.7 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.7\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2721 | 2822 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| 1.21.8 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.8\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2721 | 2822 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| 1.21.9 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.9\mojmap\net\minecraft\world\entity\LivingEntity.java` | 2751 | 2852 | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |

Each cited callback line is within the listed `aiStep()` method and follows its travel call. The exact lines retain the original client-side/local-authority guard. The 1.21.6, .7, and .8 `LivingEntity.java` sources are byte-identical, as shown by their shared hash.

## Scope

This supplement corrects only the method name and binds it to the exact source references and hashes above. It makes no new pair-freeze, full-coverage, or runtime-parity claim. The original report and independent audit remain unmodified.
