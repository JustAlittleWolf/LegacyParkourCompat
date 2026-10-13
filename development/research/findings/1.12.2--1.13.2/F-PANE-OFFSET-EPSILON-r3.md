# F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary (float and shape corrected snapshot r3)

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 54-75.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/util/math/Box.java`, `public double intersectX(Box box, double limit) {`, lines 185-203.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, `public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {`, lines 82-84.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java`, `public double calculateMaxDistance(Direction.Axis axis, Box box, double maxDist) {`, lines 184-186.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java`, `protected double calculateMaxDistance(AxisCycle axisCycle, Box box, double maxDist) {`, lines 188-243.

Difference: Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

Movement meaning: Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity d.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-COLLISION-REPRESENTATION — 1.13.2 changes player collision resolution representation; later broadly related hooks do not establish this claimed delta.",
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary (float and shape corrected snapshot r3); later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(MoverType moverType, double x, double y, double z) {",
      "lines": "462-795",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 2a74faa4005108a1dbda32b0799522b2feb786f19fa9ad3b3bba9d40e1ff4687."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {",
      "lines": "964-1015",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7e6fc52e791970a1434bf0384cce5fafea6fab8e419852f612b517c8432fae55."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(@Nullable Entity entity, Box shape) {",
      "lines": "1017-1040",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 629c744b573939adc1cd2f676fcec026d55a13e6762adc01eb2d6ce2f36cf25f."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean getCollisions(Box shape) {",
      "lines": "1062-1064",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 0a28414321edc341d45591b03ad761b1f7519dc1440b32293f1c49e6ac2e7324."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "WorldView#getBlockCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather",
      "path": "1.12.2/ornithe-feather/VoxelShapes.java",
      "member": "VoxelShapes#calculateMaxOffset",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(MoverType moverType, double x, double y, double z) {",
      "lines": "473-776",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4a2270fbb434f7bde4f2f15062993063560bcda5c372723ee5cdf081fc63cff8."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public Stream<VoxelShape> getCollisions(@Nullable Entity entity, VoxelShape shape, VoxelShape excludeBlock, Set<Entity> excludeEntities) {",
      "lines": "1755-1758",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c47ed7a5dceea59f2d27209c9123378b31b25a69be66a37f30284904f2251294."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "default Stream<VoxelShape> getBlockCollisions(VoxelShape shape, VoxelShape excludeBlock, boolean checkWorldBorder) {",
      "lines": "112-152",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ff887bf9c999974aa07f28e9273830f2e4cdf620b6c1ac1ae0e4b73de12cafb1."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java",
      "member": "public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {",
      "lines": "194-206",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 18215f5bcc9774b93ec9b17056402184c8b8e59aa67e6d516f41e5010443a2b6."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java",
      "member": "PaneBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {",
      "lines": "54-75",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ce23f64bec49e2f15a9e1d211eb88f2b6f1fd307f202c66a6d5421e045e6ebb2."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather",
      "path": "1.12.2/ornithe-feather/VoxelShape.java",
      "member": "VoxelShape#calculateMaxDistance",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/util/math/Box.java",
      "member": "public double intersectX(Box box, double limit) {",
      "lines": "185-203",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 33e1cf4f78413e9986450d7c8a77ef25a4e35e7ac1b703a2a973a0136670179a."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/PaneBlock.java",
      "member": "public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {",
      "lines": "82-84",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 afd3d9629b932b1c8bbd2cc36c37e335b2c483e572ce4644b4b5b71b9dd6ef74."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/PaneBlock.java",
      "member": "PaneBlock#addCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java",
      "member": "public double calculateMaxDistance(Direction.Axis axis, Box box, double maxDist) {",
      "lines": "184-186",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 f44129b284e85a6b40ead947ca58a05320b5503ce34a1761cae66fec26d0a4cd."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java",
      "member": "protected double calculateMaxDistance(AxisCycle axisCycle, Box box, double maxDist) {",
      "lines": "188-243",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 01cbe6b2e41b2781af953b59b7b910e1bb2f1e72e1b6aab778008f69a664db08."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/util/math/Box.java",
      "member": "Box#intersectX",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "source-review-decision",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "kind": "source-review-decision",
        "id": "1.12.2-1.13.2/F-PANE-OFFSET-EPSILON-r2",
        "state": "Second blind review REQUEST CHANGES. Corrected float bounds pass, but BArrayVoxelShape fallback uses a 7/16,9/16 subdivision outside the supported findBits subdivision. Owner is tracing/correcting and continuing full remaining inventory; no nested reviewer should be spawned.",
        "snapshotCommit": "ba083b420233bab6c0e3ac5ed2f9f7a55110908f",
        "snapshotPath": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON-r2.md",
        "snapshotBlob": "d8900b7b9e5e40a2cc15a93b11fedc90eca1577e",
        "snapshotSha256": "3a4e6547364c60418f50b121a91485f454a3af1026d1a3df2fcb396000ac4a51",
        "reviewDecision": "REQUEST CHANGES",
        "nestedReviewer": "root/pane_r2_blind_review",
        "nestedReviewerModel": "Luna High",
        "nestedReviewerStatus": "completed; role released",
        "limits": [
          "A clipping value is supported only for the tested shape path.",
          "Original mapped-JAR equivalence remains unverified.",
          "Pair remains PARTIAL."
        ]
      }
    },
    {
      "verdict": "REQUEST CHANGES",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "kind": "source-review-pending",
        "id": "1.12.2-1.13.2/F-PANE-OFFSET-EPSILON",
        "state": "Immutable snapshot REQUEST CHANGES by independent review 91193a2c. Correct a bounded geometry/state witness and continue source inventories; retain the A ready-marker hash caveat and unavailable original mapped-JAR equivalence caveat.",
        "snapshot": {
          "commit": "e10bc0dd046cb968564301eca5986ce81c77c6c7",
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md",
          "gitBlob": "f5322d10366c848889c200a40508ec8a273ba25e",
          "sha256": "5941f0b4fc505646e18c2b275cc372b50645ac3151ab1982776e47c0fe176dca"
        },
        "review": {
          "commit": "91193a2c01f4ca8f0ae6efb48ba1ea41b8888eda",
          "path": "workflows/source-campaign-2026-10-07/independent-review-F-PANE-OFFSET-EPSILON-2026-10-08.md",
          "gitBlob": "ffbb7111235b0c8481bcdc4b6f1083e55a64fe56",
          "sha256": "6fbf55df657a9458b30a7caedae2bdeedc4a79bdc6d07e87451e28cd620f576c",
          "decision": "REQUEST CHANGES"
        },
        "ownerThread": "01a11bb3-9efb",
        "reviewerThread": "01a11bbe-a775-7180-a017-d2386dcd9fb3",
        "nextLookup": "Publish corrected bounded snapshot and continue dependency inventories before a fresh review."
      }
    },
    {
      "verdict": "REQUEST CHANGES",
      "assertion_preserved": true,
      "detail": "Shortened wording retains the bounded behavior assertion of this exact historical snapshot; hashes identify the original reviewed bytes only. Source-content transfer does not assert mapped-JAR equivalence, implementation fidelity, patch cutover or runtime parity.",
      "identity": {
        "kind": "source-review-decision",
        "id": "1.12.2-1.13.2/F-PANE-OFFSET-EPSILON-corrected",
        "state": "Corrected snapshot 40e3347 received REQUEST CHANGES in report b84b269e. The stated qualitative clipping difference and packet consumption are supported, but the exact A clip is 0.00000998807907104382, not 0.00001, because the player half-width is computed with float arithmetic. Preserve both earlier rejections; owner active for numeric correction and all remaining inventory.",
        "snapshot": {
          "commit": "40e3347bea75280bbbcc3a51d6e10ea2263ff054",
          "path": "workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md",
          "gitBlob": "6a36eb3fb168715fb6f3c39ad12b7d1da6b2963d",
          "sha256": "e9d211203107cdf481245798fa76be6ee5dedfdab334629e447c5c46af7d631b",
          "ownerFinalTip": "a7670819"
        },
        "reviewerThread": "01a11bbe-a775-7180-a017-d2386dcd9fb3",
        "reviewStatus": "terminal REQUEST CHANGES",
        "review": {
          "commit": "b84b269e864e97fbc6f8ea64c4d70a4dee3a7c53",
          "path": "workflows/source-campaign-2026-10-07/independent-review-F-PANE-OFFSET-EPSILON-2026-10-08.md",
          "gitBlob": "b1ba1a6ebae534c545e4666625a969edc04c9bfa",
          "sha256": "19cef9d518f527f28ccb4e3928dc7e4065052395eacc27890ea8651917ff991f",
          "decision": "REQUEST CHANGES"
        },
        "ownerThread": "01a11bb3-9efb-7962-b77b-722fbec5de31",
        "ownerStatus": "active bounded numeric correction plus full remaining inventory",
        "nextLookup": "Correct exact float half-width clip arithmetic and maintain all remaining required inventories; pair remains PARTIAL.",
        "reviewCommit": "b84b269e864e97fbc6f8ea64c4d70a4dee3a7c53",
        "reviewPath": "workflows/source-campaign-2026-10-07/independent-review-F-PANE-OFFSET-EPSILON-2026-10-08.md",
        "reviewBlob": "b1ba1a6ebae534c545e4666625a969edc04c9bfa",
        "reviewSha256": "19cef9d518f527f28ccb4e3928dc7e4065052395eacc27890ea8651917ff991f",
        "reviewDecision": "REQUEST CHANGES"
      }
    }
  ],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-COLLISION-REPRESENTATION is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-COLLISION-REPRESENTATION",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-COLLISION-REPRESENTATION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-PANE-OFFSET-EPSILON-r3 is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-PANE-OFFSET-EPSILON-r3",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-PANE-OFFSET-EPSILON-r3",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
