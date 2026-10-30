# Independent collision-lane audit — 1.9.4 to 1.10.2

Audit scope: complete bounded collision / block-provider / contact / fluid / step-edge assignment, including the farmland shape candidate and callback supplement. Blind source audit only; no product implementation, wiki material, product history, tests, build, launch, or source generation consulted/performed.

## Evidence and immutable inputs

Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`. Pair endpoint provenance/ready markers identify exact Minecraft 1.9.4 and 1.10.2, Ornithe Feather Gen2 build 2 mapping artifacts, Vineflower 1.12.0 and the pinned toolchain. Source hashes in the two input TSVs were recomputed against the canonical trees: 32/32 primary rows and 49/49 callback rows match. The source pins bind this audit to the supplied evidence bundle; changed bundle claims require renewed audit.

Immutable candidate finding input SHA-256 (provided): `90b33db296850639a5b59a0777c445f6936a2fc544ddf0bdbb662610a292bbd2`. Immutable callbacks supplement SHA-256 (provided): `a6a13450e947e4746c764b08fbd22d75c852a95bce670d3c0810add15ad74808`.

## Independent retrace and verdict

**Finding accepted for source evidence.** In 1.9.4 `net/minecraft/block/FarmlandBlock.java`, `getShape` returns the 15/16-high `SHAPE` while `getCollisionShape` explicitly returns `FULL_BLOCK_SHAPE`; in 1.10.2 the override is absent and `Block.getCollisionShape` delegates to `getShape`. `Block.addCollisions` (1.9.4 lines 336–348; 1.10.2 lines 355–367) translates the chosen local box and adds it when it intersects the swept player box. Thus, when a descending player sweep reaches farmland, its collision top is block Y+1 in 1.9.4 and Y+15/16 in 1.10.2. This is a direct reachable player collision delta; downstream trajectory is not claimed. The exact endpoint interval patch introduction remains unknown.

Independent paired retrace found no additional assigned lane delta in: `Entity.move` collision query, support-edge reduction, axis clipping order (Y/X/Z), step-up alternate paths and squared-distance selection; `World.getCollisions`, `getBlockCollisions` and boolean collision predicate; `Box` expansion/movement/axis intersection/overlap/contraction used by movement; default state collision-shape delegation and translated collision insertion; era-existing provider geometry and relevant neighbor-dependent fence/gate/wall shapes; post-move support/fall/step callback gates, callback order and footprint scan; existing block contact callback bodies; climbing block predicate; liquid presence/material predicates, bounds/loaded gate, fluid scan and flow accumulation. The source spelling change from local double `0.05` to literal `0.05` preserves the same comparisons and subtraction. Snow's float expression preserves multiply type/order. Other observed provider-file differences were outside geometry/contact operations inspected. The newly added MagmaBlock callback is damage-only and the block is absent in the older release, so it is excluded by the assigned existing-era movement scope.

Exact source methods inspected are pinned by input `source-hashes.tsv` and `callbacks-source-hashes.tsv`. The supplied inventory's explicit caveats remain: other tick/travel/modifier consumers belong to another lane, and this is not complete pair coverage. No unresolved dependency blocks the farmland shape finding itself.

## Stable-release cutover probe

Read-only `1.10` and `1.10.1` Ornithe Feather source trees are present. Farmland source hash in 1.10 is `9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25`, matching the supplied 1.9.4 file with the explicit full-block override. In 1.10.1 it is `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`, matching the 1.10.2 file without the override. Therefore the observed source change is absent in 1.10 and present by 1.10.1.

**No stable cutover accepted.** Unlike the exact endpoints, the available 1.10 and 1.10.1 trees lack adjacent exact-run provenance, mapping artifact identity, ready marker and source manifest in their version roots. Directory names and identical source text/hashes alone do not prove exact release or mapping pins under the source workflow. Keep the accepted cutover interval at 1.9.4–1.10.2 (unknown patch boundary) until canonical exact-source pins for the intermediate releases are supplied and verified.

## Bounded coverage gates

- Farmland source finding: independently accepted, bound to the candidate SHA and endpoint evidence above.
- Assigned collision/contact/fluid/step-edge lane: inventory audited with no other accepted delta; its closure is bounded to the assigned slices and listed dependencies.
- Full 1.9.4–1.10.2 pair: not frozen by this audit; other movement lanes and their independent coverage audit remain required.
- Exact stable cutover: open pending exact 1.10 and 1.10.1 source/mapping provenance and source manifest pins.
