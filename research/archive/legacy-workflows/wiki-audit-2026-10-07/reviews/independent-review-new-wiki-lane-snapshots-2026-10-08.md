# Independent review: new Wiki-lane snapshots (2026-10-08)

## Review scope and binding

Independent review of the Wiki-only catalog/checkpoint and the five bounded records at owner tip `ef081c48a29eb98405b4031803441cd0af31ecae`. This review checks the fixed connected-shape mask matrix, one pane/Ice neighbor-provider witness, bounded swimming/crawling pose behavior, later creative-flight input handling, and the numeric jump-apex recurrence. It does not close the Wiki lane or the source pair.

Decisions below bind to the exact immutable snapshot blobs where those are distinct from the owner-tip copies. No implementation, ordinary pair-finding report, MCPK record, or non-canonical source artifact was used. The source checks used only exact ready trees under `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather` and their manifests.

Owner-tip evidence bindings:

| File at `ef081c48a29eb98405b4031803441cd0af31ecae` | Git blob | Bytes | SHA-256 of raw file |
|---|---|---:|---|
| `workflows/wiki-audit-2026-10-07/CHECKPOINT.md` | `99313f05c1da74f6f88c2da5013788cc5f6339e8` | 14,029 | `4856198b89527fe8d98f5f4683001b0e9d416eef386363d5e0a81cd09e3e4bad` |
| `workflows/wiki-audit-2026-10-07/minecraft-wiki.md` | `e777fb32f56187a5ac1afc966028e397899936ac` | 44,799 | `2208bf3ce5fb2c03b45b3e396d0c608ea7f3d66f4a9e1170773808ad20d9d938` |
| `workflows/wiki-audit-2026-10-07/wiki-page-fetch-log.md` | `75852435f5814d2dfd776eabad033db6cf021c45` | 4,035 | `133fbf4e21214d3d7017ddcf4696d04a0fabd13d9cb8b417e1512d2691ba4131` |
| `workflows/wiki-audit-2026-10-07/WIKI-CHECKPOINT-LEDGER.md` | `e6e5c36d1e9379962d8b9408988bc3acc07280b4` | 1,520 | `685b66eca76dfbab1805614dd855a45fac2b54baabd9e3582b93a890daba8e7b` |

The ledger is included only as a checkpoint binding; this review makes no use of its unrelated lane contents.

## Claim decisions

### 1. Connected shapes: 48 fixed-mask comparisons — ACCEPT, bounded

Snapshot binding: commit `9fbb393269a023cdf60370b584da6860a87e3483`, blob `0ec9a62114fae4790719f018a078d33db928be79`, 6,933 bytes, raw SHA-256 `83ce9c03847e501fb640e584fbd2643dce346eb025054ffbb57456c199408fd4`. The owner-tip copy has blob `e056e9e300c330305fd9d7e8401305a75fec6f22`, raw SHA-256 `ac7b646665316106367dfa4602a07fef3bef02391acd9a11f3718216452278cb`; comparison shows only a follow-up paragraph changed to cross-reference the pane/Ice record. The matrix and its source basis are unchanged. This decision binds to the frozen snapshot blob.

The source-derived matrix is internally consistent for the stated fixed-mask question: across 16 masks each for pane/bar, fence, and wall, pane/bar differs in 9 masks and matches in 7; fence matches in 16/16; wall matches in 16/16 when the horizontal mask is held equal. The pane/bar changed masks are no connection, each cardinal singleton, and each adjacent two-direction corner. Straight-through pairs, three-way masks, and the four-way mask match. Fence and wall equality is conditional on equal masks.

This is a fixed-geometry comparison, not proof that the two versions produce equal masks from every neighboring block, nor a complete collision-provider inventory. Dynamic neighbor providers, wall `UP` behavior, and all remaining registrations/providers stay open. Do not promote the 48-row result into an all-world collision-equivalence claim.

### 2. Pane/Ice neighbor provider — ACCEPT, one witness only

Snapshot binding: commit `be35bbec3444ccac3da4f9d8193a0a4dc94434df`, blob `6adc2a3b273bca0bfc24747b8b29083acd213494`, 4,244 bytes, raw SHA-256 `5cf32f58e7f0dc63e21f30a1476f23ba6e10930c4c5759cb89c89cf60d5a0d65`.

The canonical source comparison supports the specific Ice witness. In 1.8.9 the pane predicate rejects Ice because its non-opaque status fails the general connection test and Ice is not an exception. In 1.9.4 the predicate asks whether the neighbor default state is a cube; Ice inherits the default cube result. With Ice east of a pane, the east arm is therefore absent in 1.8.9 and present in 1.9.4. The resulting pane collision boxes differ, and the normal entity movement collision query reaches block collision providers, so this is movement-relevant geometry.

The conclusion is limited to this provider and arrangement. It does not establish a complete provider list, every Ice-adjacent state, or all dynamic pane masks. The report preserves that open dependency.

### 3. Swimming/crawling pose fallback — ACCEPT, bounded pose selection

Snapshot binding: commit `d0efc827320061d66dd3362b5396cce21d72284d`, blob `101a90ead9b57a00e503102c7a875a6089b55658`, 6,571 bytes, raw SHA-256 `e3573ef4af75643b3c69e49673c1e5aea99917b53008f649b35332a1101c961c`. The owner-tip file is the same blob.

The reviewed 1.13.2→1.14.4 source slice supports the bounded fallback: 1.14.4 tests swimming dimensions for clearance, then the desired pose, then sneaking dimensions, and falls back to swimming-sized dimensions if neither desired nor sneaking pose fits. Since the swimming dimensions are short, this can select the swimming pose outside water. The checked swim-state updater does not establish a broader change to water eligibility; it is shared in the examined slice. The record properly treats this as pose selection, not a complete swim/crawl transition or movement timeline.

All pose writers and consumers, dimensions and eye-height consequences, and movement interactions remain open. Keep the source-pair status partial.

### 4. Later creative-flight input branch — ACCEPT, input branch only

Snapshot binding: commit `2911508f46232108071b2833773a58e371764d7a`, blob `1b9cad78c04073c021e4c3ebc99e84ba4fd3c5bd`, 5,608 bytes, raw SHA-256 `dcedd764c59040805a9ba060583ca68bdf34625e7cce26d096283c4c4f46f706`. The owner-tip file is the same blob.

The six endpoint comparison is supported by the canonical LocalClientPlayerEntity slices from 1.9.4 through 1.14.4. The five earlier endpoints use sequential sneak subtraction and jump addition of `getFlySpeed()*3.0F`; 1.14.4 aggregates those inputs into an integer and applies one vertical addition only when the aggregate is nonzero. Thus simultaneous sneak+jump reaches vertical writes in the earlier endpoints but no vertical write in 1.14.4. The fluid guard behavior described in the record is also supported in those checked slices.

This accepts only that bounded input branch and guard. It does not establish net velocity, displacement, full creative-flight behavior, or all eligibility/attribute dependencies; those stay open as the snapshot states.

### 5. Numeric jump-apex calculation — ACCEPT, source-ordered bounded recurrence; Wiki provenance remains incomplete

Snapshot binding: commit `ab102b5d5da292e76460801ab362711acd734996`, blob `6959522c8e79dfccd81732f2e03ddc1d3390106d`, 4,803 bytes, raw SHA-256 `3a413b1e0e62cb5da276a6209779e60c3bd97874d7c166f5999142f067bdfd3a`. The owner-tip copy is blob `ec74e4c7b6f97efbdeac71a7324394b46af33675`, raw SHA-256 `81c2cbe313a6419da1f0a21c7733d53a809ceb6976ae77c6e9852cd6308f677a`; only the status line changed. The recurrence and numeric content are unchanged. This decision binds to the frozen snapshot blob.

Independent source-ordered single-precision recurrence reproduces the report's bounded result: the 1.8.9 local ground-jump path uses `0.42F` and cuts each velocity component below `0.005` before the jump branch; 1.9.4 uses the same jump strength and a `0.003` cutoff. Under the report's normal, unassisted, local ground-jump and empty-air assumptions, the height before the sixth displacement is about `1.24919`; the remaining vertical velocity is about `0.00301626`, so the 1.8.9 cutoff removes it while the 1.9.4 cutoff preserves it, yielding about `1.25220`. The numerical recurrence is accepted only under those assumptions and the source's operation order. It is not a general jump trajectory or a new movement-mechanic finding beyond the bounded cutoff consequence.

**Wiki attribution subclaim — REQUEST CHANGES.** The fetch log records that the Jumping page fetch was blocked and provides no exact page revision ID. An official `Template:Sandbox/HistoryLine` search result reports 15w45a `1.24919`→`1.2522`, but that is a search-result lead, not a verified Jumping-page revision. Keep direct Wiki-page provenance marked unverified/Wiki-only until an exact page revision is obtained. The owner-tip checkpoint/catalog correctly remain partial and pending reconciliation.

## Exact source-tree bindings checked

For each version, the ready marker, source manifest, and artifact manifest were checked together; the marker's embedded hashes matched the exact manifest files. Selected source-file hashes were rehashed against source-manifest rows. Relevant ready-set bindings:

| Version | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|
| 1.8.9 | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` |
| 1.9.4 | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| 1.10.2 | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` |
| 1.11.2 | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` |
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |
| 1.14.4 | `bf8c9399e3a36ddd7edc78d98413319000895f49e88db224335f62b2baf21e77` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |

Selected collision-path source hashes (SHA-256, canonical ready tree):

| Version | File | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 | `FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.8.9 | `WallBlock.java` | `e1fca4e4dbb7736d17116e4dee92064d35f7bf135da374dae0615e89216aca5e` |
| 1.8.9 | `Direction.java` | `ede4dc29795263853c048e569f24e74407255767c5e02349a90895ac5b08e544` |
| 1.8.9 | `IceBlock.java` | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` |
| 1.8.9 | `TransparentBlock.java` | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.8.9 | `Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| 1.8.9 | `World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.8.9 | `PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| 1.9.4 | `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `FenceBlock.java` | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.9.4 | `WallBlock.java` | `af01294fe9391b77e54fcc30696f8b80754fc5b8f25f3e933aa3c94a2f101640` |
| 1.9.4 | `Direction.java` | `10bd074fa46e27658e7109836fe5456fa1f2017a15401f28fc12900d942f02ef` |
| 1.9.4 | `IceBlock.java` | `23502fdd9ae0d32687fdd9fed4bf272bede481ad00e371c6fc7d9a76f6d87ba4` |
| 1.9.4 | `TransparentBlock.java` | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 | `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |
| 1.9.4 | `Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| 1.9.4 | `World.java` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.4 | `LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.4 | `LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |

The movement-consumer path is present in both checked versions: entity movement requests world collisions for its moved/expanded bounding box; world collision enumeration dispatches to the intersecting block state's collision provider. This establishes reachability for the pane witness, not completeness of the world/provider inventory.

Selected creative-flight source hashes (canonical ready tree, all match manifest):

| Version | `LocalClientPlayerEntity.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|
| 1.9.4 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| 1.11.2 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.12.2 | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13.2 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |

Selected swim/crawl source hashes (canonical ready tree, all match manifest):

| Version | `PlayerEntity.java` | `LivingEntity.java` | `Entity.java` |
|---|---|---|---|
| 1.13.2 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |

## Wiki fetch provenance and lane status

The owner fetch log gives these exact revision bindings for the pages relevant to this slice: Glass Pane `oldid=2728132`; Fence `oldid=2939254`; Flying `oldid=2732007`; Swimming `oldid=2726686`. It records stale cached responses for those pages. Crawling and Jumping have no retrieved revision IDs; direct requests returned internal error/robots restrictions. The Iron Bars, Wall, and Hitbox entries also lack a usable exact revision in the log. Failed or stale retrieval is not evidence that a claim is absent from a page.

The independent current web attempt could not reopen those oldid pages. The official HistoryLine search result exposes the 15w45a rounded jump-height delta `1.24919`→`1.2522`, but this does not supply the exact Jumping-page oldid or establish its full text. Treat this as a lead only: [Minecraft Wiki Template:Sandbox/HistoryLine](https://minecraft.wiki/w/Template%3ASandbox/HistoryLine).

The checkpoint correctly remains **PARTIAL**. The independent decisions accept bounded source comparisons; they do not certify Wiki-lane completion, full source-pair discovery, or implementation coverage. The catalog owner should reconcile the reviewed bounded rows while retaining unresolved page provenance and provider/pose/flight dependencies as open.

## Review execution

Read-only review of owner records and canonical ready source trees; no owner file was edited. No tests, builds, decompilation, runtime, game/TAS, server, Docker, or push was performed.
