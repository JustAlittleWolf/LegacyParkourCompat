# Wall collision drafts: source review closure

**Verdict: ACCEPT, bounded to the source-level wall collision-shape change and the two reviewed masks.** The drafts are two geometry witnesses for the same `WallBlock.getCollisionShape` operation family, not evidence for separate shape-selection operations. This review does not assert a natural input trajectory or runtime parity.

## Bound inputs and source identity

Reviewed draft revisions (SHA-256):

| Draft | SHA-256 |
| --- | --- |
| `wall-single-arm-gaps.md` | `e051f4920fcb35e4745663601aa647572f9ca0d3d5bb0e2ec581ab1c857d0ac8` |
| `wall-three-arm-corner-gaps.md` | `432dc582a6d8286318d5eca156e7d26c4968bc4ab7eb9ab03ecede5f41df4531` |

The read-only canonical source root is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Each release is Ornithe Feather. Provenance and ready records identify the exact releases and mapping coordinates; the corresponding source-manifest files match the `sourceManifestSha256` in each ready record. Mapping binaries themselves were not reopened in this bounded review.

| Release | Resolved mapping coordinate | Provenance JSON SHA-256 | Ready JSON SHA-256 | Source-manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.12.2 | `feather-gen2:1.12.2+build.2` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `feather-gen2:1.13+build.2` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `feather-gen2:1.13.1+build.2` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `feather-gen2:1.13.2+build.2` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Targeted Java files were recomputed and matched their release manifests. The geometry implementation is an exact alias across 1.13, 1.13.1 and 1.13.2: `WallBlock.java` SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`; `PaneBlock.java` `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`; `FenceGateBlock.java` `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`; `Direction.java` `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082`. Registration remains `cobblestone_wall` and `mossy_cobblestone_wall` as `WallBlock` in all three releases (`Block.java`: 1.13 `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476`, 1.13.1/.2 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `Blocks.java`: 1.13 `8e66b1ad391d61f4e42fecb313f99299810493cb87525493f9ace8a1eb7cd000`, 1.13.1/.2 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`). The differing 1.13 registration-file hashes reflect other source changes; the two wall registrations and exported fields are textually present in each release.

The 1.12.2 `WallBlock.java` SHA-256 is `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`; its corresponding registration is block ID 139 and `getStateFromMetadata` supplies the normal/mossy `VARIANT`. Exact query-route sources used below: 1.12.2 `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`, `LivingEntity.java` `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`, `LocalClientPlayerEntity.java` `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`, `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`. 1.13.2: `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`. The 1.12.2 fence-gate source hash is `38ae9e6aa1057a8e9b65ff78414d0d6a9a70cf2645f1ea42141ff2815ad6a477`; the 1.13/1.13.1/1.13.2 alias is `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`.

## Source result

In 1.12.2, `WallBlock.java:28–63, 84–103, 105–123, 146–160, 189–201` stores one `Box` per four-bit neighbor mask. Collision selection recomputes the neighbor connections, then indexes with N/E/S/W direction bits; the computed `UP` does not enter `getShapeIndex`. The north-only mask is 4 and uses `SHAPES[4] = [0.25,0,0]–[0.75,1,0.75]`, extended to 1.5 high by `COLLISION_SHAPES`.

In 1.13/1.13.1/1.13.2, `WallBlock.java:22–39, 51–66, 68–93, 95–124` keeps the same four connections but reads stored placement/update properties. `UP=true` selects its 1.5-high wall collision table; `UP=false` falls back to the pane collision shape. `PaneBlock.java:38–73, 86–108` builds each index as a union of a central post and selected arms. With wall parameters `f=4`, `g=3`, an arm is 6/16 wide and the center post is 8/16 wide. Direction bits yield north-only mask 4 and N/E/S mask 13 in every inspected release.

Both draft configurations are valid vanilla placed states. A wall next to one open fence gate on its north side has north=true and other sides false; the same gate adjacency pattern on north/east/south gives mask 13. The wall placement/update formula evaluates `UP=true` for both patterns with air above. `WallBlock.shouldConnectTo` admits a gate when its queried face reports `MIDDLE_POLE`; both old and new `FenceGateBlock.getFaceShape` produce that face for a matching horizontal facing (`1.12.2:174–180`; `1.13.x:158–165`). Open gates are collision-empty (`1.12.2:81–87`; `1.13.x:67–73`), so they provide valid nonblocking connection neighbors for the movement witnesses below. This avoids assuming that a solid full-block neighbor leaves the player approach unobstructed.

The 1.12.2 connection predicate queries neighbor face shapes in `WallBlock.shouldConnectTo`; the 1.13 placement and update paths do likewise. It is accurate to describe the two reviewed states as neighbor-derived. The inspected endpoint difference is between 1.12.2 and 1.13. No uninspected snapshot or intermediate build boundary is inferred.

## Conditional player collision witnesses and wording limits

The operation is reached by normal player self-movement: `LivingEntity` calls `move(MoverType.SELF, ...)` (1.12.2 lines 1464/1514/1543/1574; 1.13.2 lines 1523/1575/1605/1640), and local-player `move` delegates to `super.move` (1.12.2 lines 846–850; 1.13.2 lines 871–875). `Entity.move` obtains world collision geometry and clips requested axes against it (1.12.2 lines 567–598; 1.13.2 lines 578–598). The old world collector invokes block `addCollisions` for the swept box (`World.java:964–1021`); the 1.13.2 entity movement path calls the world collision query (`WorldView.java:154–177`).

Concrete source-level conditions, with the player airborne so the step-up branch is not entered, no other collision candidates/entities, loaded interior chunks and no nearby border:

- **North-only mask 4:** wall at `(0,64,0)`, one open gate at `(0,64,-1)`, other sides and above air. For a player box `x=[0.70,1.30]`, `y=[64.1,65.9]`, `z=[-0.60,-ε]`, request `+0.5 Z`. The old arm overlaps the box's x side-band `[0.70,0.75]` as the sweep reaches z=0 and clips at the wall face. The new north arm ends at x=0.6875, so it clears the arm; the new central post begins at z=0.25 and is the first wall component reached. This supports the drafts' conditional single-arm collision response.
- **N/E/S mask 13:** open gates at the north/east/south neighbors, west and above air. Use player box `x=[0.75,1.35]`, `y=[64.1,65.9]`, `z=[-0.60,-ε]`, request `+0.5 Z`. The old mask-13 box spans x=0.25..1 and z=0..1, so it clips at z=0. The new north arm ends at x=0.6875 and the post ends at x=0.75; this box clears both. The first intersecting component is the east arm at z=0.3125. This is a bounded collision-query consequence, not a claim that the box and request arise from one ordinary input tick.

For the three-arm wording, the old box fills the northeast and southeast exterior corners, while the new union leaves those portions beyond the east arm open. “West-side sliver” is accurate only for z outside the post's central z span (below 0.25 or above 0.75); the center post fills the west strip between those heights. Keep that region explicit if the draft prose is revised. The single-arm 6/16-versus-8/16 statement is about the exposed north arm; the central post remains 8/16 wide.

## Remaining gates

No source-evidence blocker remains for the two reviewed endpoint geometry claims or the conditional collision-query consequence. The exact transition is bounded only by the inspected endpoints. Natural tick reachability, integration acceptance, and runtime parity remain outside this source-only closure.
