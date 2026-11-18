# Builtin dimension resource evidence: 1.21.10 → 1.21.11

Scope: exact packaged client resources for `data/minecraft/dimension_type/{overworld,the_nether,the_end}.json`, used as lava-speed inputs. Client archives were treated as ZIP containers only; no JAR code was executed. This evidence says nothing about arbitrary datapacks, runtime registry overrides, or resulting movement behavior.

## Exact packaged resource determinations

| Release | Dimension resource | `ultrawarm` field | `minecraft:gameplay/fast_lava` attribute |
| --- | --- | --- | --- |
| 1.21.10 | `overworld.json` | Explicit `false` | Absent |
| 1.21.10 | `the_nether.json` | Explicit `true` | Absent |
| 1.21.10 | `the_end.json` | Explicit `false` | Absent |
| 1.21.11 | `overworld.json` | Absent | Absent |
| 1.21.11 | `the_nether.json` | Absent | Explicit `true` |
| 1.21.11 | `the_end.json` | Absent | Absent |

The old Nether resource explicitly sets `ultrawarm: true`; the old Overworld and End resources explicitly set `ultrawarm: false`. In the newer package, none of these three resources contains `ultrawarm`. The new Nether resource explicitly sets `attributes["minecraft:gameplay/fast_lava"]` to `true`; the corresponding attribute is absent in the packaged Overworld and End resources. An absent field/attribute is recorded as absent, not as false.

The complete `data/minecraft/dimension_type/` ZIP-entry roster in both archives consists of `overworld.json`, `overworld_caves.json`, `the_end.json`, and `the_nether.json`. There is no separately packaged `data/minecraft/dimension_type/default.json` in either archive. `overworld_caves.json` is outside this assignment's three-resource extraction. The exact package therefore supplies no separate dimension-type `default.json` whose role could be reported; no broader defaulting behavior is inferred.

## Artifact identities

Official manifest-selected version metadata URLs and SHA-1 expected/actual values, client URLs and SHA-1 expected/actual values, archive byte sizes, and local SHA-256 values are recorded in `artifact-pins.json`. Metadata SHA-1 matched the manifest entry for each exact version. Client SHA-1 and byte size matched each version metadata record.

## Raw extracted resource hashes

`resource-roster.json` records exact entry status, output path, raw uncompressed byte length, and SHA-256 for each extracted resource. The resource bytes were copied directly from their named ZIP entries without newline, encoding, or JSON rewriting.
