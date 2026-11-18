# Independent Levitation writer continuity audit

Status: COMPLETED — ACCEPT.

## Scope and verdict

Independent source-only audit of the bounded ordinary locally controlled, non-riding, non-water/lava, non-gliding Levitation fall-distance writer from stable release 1.13 through 1.19.4. All 27 exact aliases listed below contain the same Levitation-gated reset after the ordinary movement operation. The expression adjusting vertical velocity remains `0.05 * (amplifier + 1) - prior vertical delta`, multiplied by `0.2`; the fall-distance write follows it and has no vertical-sign predicate. In 1.13–1.17.1 the site directly assigns `fallDistance = 0.0F`; from 1.18 it calls `resetFallDistance()`, whose helper body assigns `this.fallDistance = 0.0F` in every checked 1.18–1.19.4 alias.

The enclosing player movement authority gates and ordinary travel branch were checked at the assigned scope. The Levitation site follows the ordinary branch's movement call (`move(...)` in the older sources; `handleRelativeFrictionAndCalculateMovement(...)` from 1.16), so the reset is post-move. The direct fall-distance site is bypassed by the excluded fluid/gliding branches. No rider route or other fall-distance operation is included.

## Exact alias suite and source identities

All relevant `LivingEntity.java` SHA-256 values below were computed from the exact local mapping output and matched the version's `*.sources.sha256` entry or mapping-local `.decompile-provenance.json` source entry. Exact mapping selection: 1.13–1.13.2 Ornithe Feather; 1.14–1.14.3 Yarn; 1.14.4–1.19.4 Mojmap. The provenance records bind the nested cases to their release-specific client and mapping inputs. Repeated source hashes denote byte-identical complete files.

| Release | `LivingEntity.java` SHA-256 | Levitation writer |
|---|---|---|
| 1.13 | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | 1561–1564, direct float-zero write |
| 1.13.1, 1.13.2 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | 1580–1583, direct float-zero write |
| 1.14 | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | 1830–1832, `field_5902` |
| 1.14.1 | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | 1830–1832, `field_5902` |
| 1.14.2 | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | 1836–1838, `field_5902` |
| 1.14.3 | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | 1846–1848, `LEVITATION` |
| 1.14.4 | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | 1849–1851, direct float-zero write |
| 1.15, 1.15.1 | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | 1898–1900, direct float-zero write |
| 1.15.2 | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | 1898–1900, direct float-zero write |
| 1.16, 1.16.1 | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | 2026–2028, direct float-zero write |
| 1.16.2–1.16.5 | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | 2028–2030, direct float-zero write |
| 1.17 | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | 2113–2115, direct float-zero write |
| 1.17.1 | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | 2117–2119, direct float-zero write |
| 1.18, 1.18.1 | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | 2119–2121, `resetFallDistance()` |
| 1.18.2 | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | 2123–2125, `resetFallDistance()` |
| 1.19 | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | 2156–2158, `resetFallDistance()` |
| 1.19.1, 1.19.2 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | 2156–2158, `resetFallDistance()` |
| 1.19.3 | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | 2163–2165, `resetFallDistance()` |
| 1.19.4 | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | 2092–2096, `resetFallDistance()` |

For Yarn 1.14, 1.14.1, and 1.14.2, `StatusEffects.java:72` registers `field_5902` as ID 25 and registry key `levitation`; all three exact file hashes are `7beb86f7453a88422f2d445c1a1565d0f391c92c4926342202502776afef07f2`. In 1.14.3, `StatusEffects.java:78` registers the same effect under `LEVITATION`, hash `f932f61c975c3785d5826c00fc5606a21221fb2a0e29fbff4626a015eb9b8459`. All four mapping-local provenance records independently identify their release-specific source file hashes, client jars, Yarn artifacts, and Intermediary artifacts. The 1.14.0–1.14.2 symbol spelling is a mapping name, not a changed or missing effect.

For every alias from 1.18 through 1.19.4, the exact `Entity.resetFallDistance()` helper body contains only `this.fallDistance = 0.0F;`. The helper is at Entity.java:2101 in 1.18/.1, :2114 in 1.18.2, :2138 in 1.19–1.19.2, :2153 in 1.19.3, and :2173 in 1.19.4. This confirms the call-site representation change preserves the bounded float-zero state write.

## Endpoint continuity and limits

The supplied comparison's SHA-256 is `4cd09d4f9e8980a3cd663c9ae50b320013af1806a69c1f2823243c4b76a52663`; its accepted 1.19.4 endpoint remains unchanged. The 1.12.2→1.13 introduction fence remains unchanged as well. The accepted 1.19.4→1.20 pre-travel timing partition remains a separate later change and is not reclassified here.

No source identity, alias, ordering, effect-registration, or helper-body blocker remains for this bounded assignment. Exact patch versions outside the stable alias suite, riding, gliding, fluids, Slow Falling, other resets, damage, non-player/server behavior, full movement parity, and implementation are outside this verdict. No product files, backlog, wiki, or Git history were inspected or changed; no source was generated; no tests, build, or runtime were run.
