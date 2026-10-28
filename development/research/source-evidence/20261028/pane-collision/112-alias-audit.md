# Independent alias audit: 1.12 pane overlap witness

**Verdict: ACCEPT** — the bounded 1.12.2 pane-center-overlap witness and strict X clip carry over unchanged to the exact 1.12 and 1.12.1 source exports. This verdict covers only the stated isolated pane witness and the source behavior under audit.

## Scope and result

Inputs: accepted finding `.native-source/20261028/collision-dependencies/pane-center-overlap/pane-center-overlap.md` and prior audit `.native-source/20261028/collision-dependencies/pane-overlap-audit/audit.md`. I read only those inputs, the discovery/source procedures and the exact 1.12, 1.12.1 and 1.12.2 `ornithe-feather` exports.

For all three versions, an isolated pane with clear neighboring blocks has the same post-only collision box `[7/16,9/16] × [0,1] × [7/16,9/16]`. The player construction uses width `0.6F`; `setPosition` evaluates `float f = width / 2.0F` before using `f` in the double-coordinate box. Thus the supplied X gap, Z overlap and Y overlap are unchanged. `Entity.move` queries `World.getCollisions` with the movement-expanded player box, then applies the X clip after its Y clip. The collector reaches the pane post because the strict positive Z overlap makes `shape.intersects(post)` true; `Box.intersectX` has strict orthogonal Y/Z comparisons without an epsilon and therefore clips the positive X movement at the pane's minimum X.

The 1.12 and 1.12.1 exports match 1.12.2 byte-for-byte for `PaneBlock.java`, `Block.java`, `Entity.java` and `Box.java`. `World.java` differs as a whole file, but the complete `getCollisions(Entity, Box, boolean, List)` method body used by this witness (lines 964–1014) is identical across the three releases; its normalized UTF-8 text SHA-256 is `e215be418079d3089b39b24e836b71b9f7c01540b3bbcd0b976fd8bbe31efbb3` in each. The method retains the same expanded integer scan bounds, nested X/Z/Y iteration, loaded-chunk and border gates, and call to `addCollisions(..., false)`.

## Exact source evidence

All paths below are relative to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\` and use the `ornithe-feather` namespace.

- `net/minecraft/block/PaneBlock.java:26–77,109–143`: identical across 1.12, 1.12.1 and 1.12.2. Shape index zero is the center post; `addCollisions` attempts it unconditionally before conditionally adding arms. Virtual properties read the four adjacent states. Empty space does not satisfy the face-shape/explicit-block connection predicate, leaving the supplied isolated pane post-only.
- `net/minecraft/block/Block.java:372–381`: identical. `addCollision` excludes only the empty-shape singleton, translates the pane box and appends it when the query box intersects it.
- `net/minecraft/world/World.java:964–1014`: collector method body identical as described above. The witness query covers block `(0,64,0)`; there are no other colliders by premise.
- `net/minecraft/entity/Entity.java:178,298–305,567–590`: identical. Base width is `0.6F`; `setPosition` computes the float half-width; `move` gets collisions from the swept box and performs the X pass using `Box.intersectX`.
- `net/minecraft/util/math/Box.java:185–203,245–251`: identical. X clipping requires strict Y/Z overlap and uses no inset; collision collection uses strict positive overlap on all three axes.

Recomputed full-file SHA-256 values (1.12 = 1.12.1 = 1.12.2 for the first four files):

| Source file | SHA-256 |
|---|---|
| `PaneBlock.java` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
| `Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

`World.java` full-file hashes differ because each release contains unrelated changes: 1.12 `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`; 1.12.1 `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259`; 1.12.2 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`. The body-specific comparison above is why this whole-file difference does not affect the collector result.

## Source identity and limit

The 1.12 and 1.12.1 exports each contain `.decompile-provenance.json`. Its hashed inputs identify the corresponding Mojang client jars and Feather metadata/mapping artifacts:

- 1.12: client object `909823f9c467f9934687f136bc95a667a0d19d7f`, client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; Feather `1.12+build.2` mergedv2 artifact SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`.
- 1.12.1: client object `83385d346cf7f97d1e447b888750fb88e9928b93`, client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`; Feather `1.12.1+build.2` mergedv2 artifact SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`.

I verified the target source contents by recomputing their hashes and checked that the provenance records name distinct exact release inputs. The 1.12 and 1.12.1 export roots do not include per-source checksum manifests; the recorded input hashes therefore establish metadata-backed provenance, not a fresh revalidation of mapping artifact bytes or a cryptographic binding of each emitted source file to those inputs. For 1.12.2, the existing ready/source manifest records are present and the five relevant source hashes above match its entries. This provenance limitation does not reveal a source-behavior mismatch; the alias verdict is based on the inspected source bodies.

No implementation, build, test, gameplay/runtime launch or source generation was performed. No trajectory or broader version-line equivalence is claimed.
