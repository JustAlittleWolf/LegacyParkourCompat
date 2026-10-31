# 1.18.2–1.19.4 whole-source path inventory

Read-only Mojmap source inventory for all five adjacent edges. Canonical roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The ready markers identify the exact listed releases and their source-manifest hashes; the prior source-only reconciliation independently reports every manifest row rehashed with no missing, extra, or mismatched Java files. This pass independently reconstructed case-sensitive path/hash differences from all six source manifests and reproduced the prior edge totals.

| Release | Source-manifest SHA-256 | Java files |
|---|---|---:|
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | 4,236 |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | 4,430 |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | 4,480 |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | 4,480 |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4,618 |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | 4,740 |

| Adjacent edge | Modified | Added | Removed | Byte-identical |
|---|---:|---:|---:|---:|
| 1.18.2→1.19 | 1,530 | 305 | 111 | 2,595 |
| 1.19→1.19.1 | 253 | 59 | 9 | 4,168 |
| 1.19.1→1.19.2 | 12 | 0 | 0 | 4,468 |
| 1.19.2→1.19.3 | 1,408 | 215 | 77 | 2,995 |
| 1.19.3→1.19.4 | 925 | 146 | 24 | 3,669 |

The complete case-sensitive non-identical path/hash roster is `changed-paths.tsv`; `changed-path-roles.csv` adds a path-based source-role classification for client player, client/network, player/entity/effects/attributes, collision shapes, blocks/fluids, world providers, registries/resources, tags and residual Java. Classification is navigational only: changed paths do not by themselves imply movement semantics. The scan sees Java source manifests only; bundled JSON/tag/resource payload hashes are not available through these Mojmap source trees.

Movement-bearing ordinary-tick, collision/provider, and callback/environment member and registration closure is delegated in complementary source-only lanes. This path inventory alone leaves those semantic lanes partial; it does not establish pair freeze or runtime parity.
