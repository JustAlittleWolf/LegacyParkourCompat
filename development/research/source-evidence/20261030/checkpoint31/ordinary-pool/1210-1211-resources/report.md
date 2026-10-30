# Packaged default movement-resource comparison: 1.21.0 → 1.21.1

## Bounded verdict

With the effective resource and tag stack held equal, the packaged vanilla movement-selection inputs are byte-identical between the exact 1.21.0 and 1.21.1 client jars. There are no changed packaged tag, enchantment-definition, fluid-selection, block-selection, or other movement-parameter resource paths. This closes the assigned default-resource comparison; it does not close the release-pair source audit or establish runtime parity.

The only changed packaged `data/` paths are 1,180 generated-structure templates under `data/minecraft/structure/**/*.nbt`. These templates can change block layouts in newly generated worlds, but do not define player movement rules or block/fluid/tag selection. Their resulting world-state differences are outside the equal-world-state/effective-resource comparison here. No `assets/` entry changed. The other changed jar entries are class binaries, `version.json`, and META-INF signature/manifest entries; they are outside the packaged movement-resource comparison.

## Exact source identity and pre-entry hash gate

Both jars and their version metadata were SHA-256 hashed before opening either archive. All hashes reconciled to the supplied old-cache pins, the old embedded decompile provenance, and the new pinned client plus 1.21.1 ready/provenance/artifact manifests. No download or decompilation was performed.

| Side | Exact input | SHA-256 | Identity check |
|---|---|---|---|
| Old client | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.21/client.jar` | `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbaafa5ed2e75b04d60617e8` | Matches `client.jar.pin.json` and old embedded provenance input; 1.21.0 jar |
| Old metadata | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.21/version.json` | `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a` | Matches `version.json.pin.json`; metadata ID `1.21` |
| New client | `.native-artifacts/pinned-clients/1.21.1.jar` | `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` | Matches supplied expected SHA-256, `mojmap.ready.json` client hash, and `artifacts.sha256` `1.21.1/client.jar` row |
| New metadata | `.native-artifacts/pinned-clients/1.21.1.json` | `5603a965deddb519da59866f3df784ed11c4fef81953d22781e76cebefeac8c3` | Metadata ID `1.21.1` |

Old-side embedded provenance identity: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; it pins the 1.21 client jar above and metadata package `1.21.json` at `cdd1a44d…efa802a`.

New-side publication identity, consistent with the separate source audit:

| Manifest | SHA-256 |
|---|---|
| `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap.ready.json` | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` |
| `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap.provenance.json` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` |
| `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/artifacts.sha256` | `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` |
| `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap.sources.sha256` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |

The ready marker declares version `1.21.1`, mapping `mojmap`, 5,363 source files, metadata ID `1.21.1`, and the new client SHA-256 above. The source audit records that the ready marker, provenance and manifests reconcile. The embedded old provenance and new publication evidence identify the inputs; the resource comparison itself reads only the exact client archives and metadata.

## Packaged resource comparison

Archive inventory after the hash gate: 23,519 non-directory entries on each side; 0 added, 0 removed, 1,216 byte-content changes. Of the changed entries, 1,180 are `data/minecraft/structure/**/*.nbt`, 0 are under `assets/`, and no other `data/` resources differ. Resource equality was tested on uncompressed entry contents using SHA-256, not ZIP container offsets or compression bytes.

| Movement-relevant input family | Packaged vanilla inventory | Result |
|---|---:|---|
| Block tags, `data/minecraft/tags/block/**/*.json` | 184 | All paths and contents identical |
| Fluid tags, `data/minecraft/tags/fluid/**/*.json` | 2 | All paths and contents identical |
| Item tags, `data/minecraft/tags/item/**/*.json` | 147 | All paths and contents identical |
| Entity-type tags, `data/minecraft/tags/entity_type/**/*.json` | 34 | All paths and contents identical |
| Enchantment tags, `data/minecraft/tags/enchantment/**/*.json` | 22 | All paths and contents identical |
| Enchantment definitions, `data/minecraft/enchantment/*.json` | 42 | All paths and contents identical |
| Damage-type definitions, `data/minecraft/damage_type/*.json` | 47 | All paths and contents identical; damage calculation is excluded |
| All vanilla tag resources, `data/minecraft/tags/**/*.json` | 530 | All paths and contents identical |

Vanilla packaged resources use the `minecraft` namespace. Tag files and all tag references are byte-identical, so tag values, `#namespace:path` inheritance, `replace` behavior, and optional versus required entries do not change. With the same other data packs and priority, their resolved effective sets are unchanged. The archive contains no separate JSON registries for movement attributes, status-effect definitions, or equipment attributes; these are code/component-defined inputs in this release and are not changed by packaged data resources. Vanilla enchantment definitions and the item/enchantment tags that select them are unchanged. Block and fluid selection tags are unchanged, and there are no changed data files defining collision shapes.

## Changed paths and scope boundary

Exact changed movement-rule resource path set: **empty**.

Exact changed data-resource path class: **1,180 paths, all and only** `data/minecraft/structure/**/*.nbt`; zero changed tag, enchantment, damage-type, or other JSON data paths. These generated structures encode world composition, not movement predicates or numeric movement parameters. If scope is widened to compare independently generated worlds, their changed templates may yield differing placed block states and then need separate world-state/collision-input analysis.

## Open gates retained

- This report closes only packaged default data-resource/tag/enchantment inputs for the 1.21.0 → 1.21.1 pair under equal effective resource stacks and equal relevant world state.
- The source audit's remaining terminal coverage for collision-shape providers, callback descendants, other block/state producers, local-player dependencies and direct/external state writes remains open. Its pair-freeze and independent full-coverage gates are unchanged.
- No runtime movement parity, independently generated-world equivalence, or custom data-pack behavior is claimed.
