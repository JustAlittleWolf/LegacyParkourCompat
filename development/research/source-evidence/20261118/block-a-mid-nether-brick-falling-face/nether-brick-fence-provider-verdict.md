# Independent verdict: nether-brick fence provider, falling-water face boundary

**Verdict: ACCEPT** the bounded `NETHER_BRICK_FENCE` provider claim in every exact release listed below. The registered block uses `Material.STONE`; the pre-1.12 liquid face helper ultimately asks the state's material `isSolid()` and the nether-brick fence has no face-solid override. At 1.12, the liquid helper instead requires `FaceShape.SOLID`, while this fence returns `MIDDLE_POLE` for all horizontal directions, so both existing horizontal probes reject the default fence. This is the same old material-solid to new face-shape boundary covered by the accepted oak caller evidence.

This verdict accepts only the dry/default provider at the existing `LEVEL >= 8` horizontal side then side-above probes. I reused the supplied oak caller evidence and did not re-audit its body, flow scan, reachability or player trajectory. The inspected 1.12 source demonstrates the new behavior there; this does not establish an exact patch introduction point beyond the checked releases.

## Immutable inputs

Canonical read-only sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather`, exact Feather generation 2 build 2 mapping per release. For legacy trees, identity is the raw `.decompile-provenance.json` SHA-256; for ready trees it is `sourceManifestSha256`. The source identities and the `Block.java`, `LiquidBlock.java`, `FenceBlock.java`, and `material/Material.java` hashes were recomputed and checked against their per-release source manifests. No source tree was changed.

| Release | Source identity SHA-256 | Readiness |
|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | legacy provenance |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | legacy provenance |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | legacy provenance |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | legacy provenance |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | ready |
| 1.10 | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | legacy provenance |
| 1.10.1 | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | legacy provenance |
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | ready |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | ready |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | ready |
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | ready |
| 1.12 | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | legacy provenance |

The submitted immutable provider proof was read at `b69596cb778d2a557e466c0340c13d5be0ece7a9:nether-brick-fence-provider-proof.md`; its raw SHA-256 is `50dea01fdf165e43ae5a01d97e64bb380640b7c6db93f5bee73381ce6a7fed5b`. Its listed per-file source digest groups and line references were checked against current raw sources. The supplied oak boundary witness and verdict raw SHA-256 values also match `f7a69e9ac847e7a9e844fa685424d839eb4741c83bfc1984b6dade021cdaa7e1` and `4f64176409d0572a4a7fafcd5a6f91221134e42a486caa780c6b8ce3ff2035ae`. The supplied mid-wood sibling verdict raw SHA-256 matches `8213e4e736b15765724f587d80776a67df9adb75e96ef5ae58e8255bf787a047`; it was supporting context only and does not prove this masonry provider.

## Provider trace

- Every checked registry defines ID 113 / `nether_brick_fence` as `new FenceBlock(Material.STONE, MapColor.NETHER)` (`Block.java`: 933–937 for 1.9–1.9.2; 978–982 for 1.9.3–1.9.4; 983–987 for 1.10–1.10.2; 988–992 for 1.11–1.11.2; 1007–1011 for 1.12). The `Blocks.NETHER_BRICK_FENCE` alias resolves that registry key in each release. `Material.STONE` is a normal `Material`; the base `isSolid()` returns true. `FenceBlock` does not override `isFaceSolid`.
- In 1.9 through 1.11.2, `LiquidBlock.isFaceSolid` rejects the same liquid material, accepts UP, rejects ICE, then calls `super.isFaceSolid`; `Block.isFaceSolid` returns `world.getBlockState(pos).getMaterial().isSolid()`. Thus a dry default nether fence passes either horizontal probe regardless of its partial collision shape.
- In 1.12, `LiquidBlock.isFaceSolid` preserves the same-liquid, UP and ICE branches, then rejects `isExceptionForAttachment(block)` or stairs and requires `blockState.getFaceShape(...) == FaceShape.SOLID`. The explicit attachment exception list contains no fence. `FenceBlock`'s constructor sets NORTH/EAST/SOUTH/WEST false in the default state; its `getFaceShape` returns `MIDDLE_POLE` for any horizontal face. Therefore both side and side-above probes fail for this default provider.

## Per-release result

ACCEPT separately for 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2, and 1.12, with the behavior described above. No blocker remains within this exact release set.