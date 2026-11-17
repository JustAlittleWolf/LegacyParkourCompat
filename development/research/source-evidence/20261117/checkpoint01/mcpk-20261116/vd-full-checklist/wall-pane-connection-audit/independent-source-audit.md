# Independent source audit: 1.16 wall/pane connection clauses

Audit scope: MCPK user-supplied 1.16 clauses F, G1, and G2 in `nether-116-direct.md`. Source candidates named `walls-attach-to-panes-bars.md`, `panes-bars-attach-to-walls.md`, and `wall-soul-sand-connection.md` were not present in the assigned directory or under the campaign tree. I audited the claims from the supplied checklist and exact source. No product files, backlog, tests, or builds were changed/run.

## Evidence identity and integrity

Canonical exact Mojmap trees under `decompiled_minecraft/<version>/mojmap/` were read-only. Ready metadata binds 1.15.2 (client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`), 1.16.0 (`00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`; manifest `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`), 1.16.1 (`b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; manifest `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`), 1.16.2 (`19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`; manifest `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`), and 1.16.5 (`00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`; manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`). The source-manifest digests were recomputed from bytes. For each cited file below, the file SHA-256 was recomputed and matched its release's embedded source manifest. The relevant 1.16.1 sources match 1.16.0 byte-for-byte; the relevant 1.16.3/.4/.5 sources match 1.16.2 byte-for-byte. The 1.16.3/.4 provenance files independently list these source hashes and hash to `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` respectively.

Relevant distinct-file SHA-256 values:

| Source file | 1.15.2 | 1.16.0/.1 | 1.16.2–.5 |
|---|---|---|---|
| `world/level/block/WallBlock.java` | `4ad01bfd8d84b77abe161289294667e9f482cf9477cd81f725162aa9dc891850` | `ce153c55791f44eb92abc5f2febfbf023a3e03e63252d94201e3ab843d18b5d4` | `aa770704573ee65ca0d0b102bc55e3ece81f7b25aef452bb5c89ec0350a23442` |
| `world/level/block/IronBarsBlock.java` | `377a52caac0e4d8de9829b30e62e73430bd160ce868f1347fe414587fe2ba373` | `1e1c1e27005e4367152b46df5219aeed177e634bb03abbc1b04fd4e329c9cb69` | `e84392b278ceeaf02abb0c72c74e1011629d7c0add57f6bf0f6ec5b6006feaab` |
| `world/level/block/SoulSandBlock.java` | `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf` | `da92572788c9416b511c003e50688d802e8248ccdb3e2d5eb155aa482549b095` | same as 1.16.0/.1 |
| `world/level/block/Block.java` | `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d` | `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655` | `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6` |
| `world/level/block/CrossCollisionBlock.java` | `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd` | `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4` | same as 1.16.0/.1 |
| `world/level/CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` | `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |
| `world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |

## F — Walls connect to Soul Sand correctly (MC-172517)

**Verdict: ACCEPT — eligible, source-confirmed.**

1.15.2 Mojmap `WallBlock.connectsTo` (60–64) accepts a sturdy-face connection except excluded blocks, walls, or direction-compatible fence gates. `Block.isFaceSturdy` (419–421) tests the collision shape. `SoulSandBlock.getCollisionShape` (18–22) returns the 14/16-height `SHAPE`; its top face is not full, so it does not satisfy the wall's sturdy-face predicate.

1.16.0 Mojmap `WallBlock.connectsTo` (130–134) and placement/update callers (137–207) retain the sturdy-face branch. `Block.isFaceSturdy` (228–230) now tests the block support shape. `SoulSandBlock` preserves the same 14/16 collision shape (18–27) but adds `getBlockSupportShape` returning `Shapes.block()` (29–32). Thus the neighboring wall's directional property is true where it was false in 1.15.2; the same code and result persist in 1.16.1–.5.

Movement reachability: `WallBlock.getCollisionShape` uses its state to choose the precomputed wall collision shape; a connected side contributes its corresponding arm. `Entity.move` uses `collideBoundingBoxHeuristically`, which obtains block collision shapes from `CollisionGetter.getBlockCollisions`; `CollisionGetter` queries `BlockState.getCollisionShape`. This is a direct eligible local-player collision difference for existing blocks. No auxiliary tag or resource membership is needed. Exact difference is present in 1.16.0 and persists in inspected releases; no later fix boundary was asserted or inspected.

## G1 — Panes/bars attach to walls

**Verdict: ACCEPT — eligible, source-confirmed as a distinct direction of connection.**

1.15.2 Mojmap `IronBarsBlock.attachsTo` (75–78) allows non-exception sturdy-face neighbors or another `IronBarsBlock`. In 1.16.0 the predicate (85–88) adds `block.is(BlockTags.WALLS)`. Placement (23–41) and horizontal `updateShape` (43–56) use this predicate to set the pane/bar's direction Boolean. `StainedGlassPaneBlock` extends `IronBarsBlock` (source lines 6+); `Blocks.GLASS_PANE` is instantiated as `IronBarsBlock` (Blocks.java 926+). The 1.16.5 `BlockTagsProvider` registers wall blocks in `BlockTags.WALLS` (338 onward), verifying the required tag dependency rather than assuming it. Relevant behavior persists in .1 and .2–.5 file groups.

`CrossCollisionBlock.getCollisionShape` indexes collision shape by those direction properties (1.16.0 lines 89–97, 103–123); therefore the new wall-directed arm changes the vanilla pane/bar collision shape. `Entity.move` consumes block collision shapes through the path described in clause F. This is an eligible local-player collision difference. This predicate/state change is independent from G2: G1 mutates the pane/bar state when its neighbor is a wall. The supplied clause provides no fix release; no fix boundary is claimed.

## G2 — Walls attach to panes/bars

**Verdict: ACCEPT — eligible, source-confirmed as the reciprocal but independent direction of connection.**

1.15.2 Mojmap `WallBlock.connectsTo` (60–64) has no `IronBarsBlock` branch; its other connection paths are sturdy face, wall tag, or direction-compatible gate. In 1.16.0 the method (130–134) adds `block instanceof IronBarsBlock`. The wall's placement caller (137–157) and side-update caller (192–210) pass the neighbor through this predicate to compute directional wall properties. The 1.16.5 `WallBlock` shape maps (`shapeByIndex` and `collisionShapeByIndex`, 36–42; lookup methods later in the class) are keyed to blockstate; `getCollisionShape` selects the shape for that state. `IronBarsBlock` covers iron bars and both ordinary/stained glass panes via `Blocks.GLASS_PANE` and `StainedGlassPaneBlock` (see G1). Accordingly the new type branch changes the wall's neighbor-directed property and collision shape. `Entity.move` consumes the shape for player collision as described in F. This is an eligible local-player collision difference.

G2 is not the same state mutation as G1: G2 mutates the wall's directional state when a pane/bar is its neighbor. The addition is present in 1.16.0 and persists across the inspected 1.16 source groups. The supplied clause gives no fix release, so none is claimed.

## Scope and remaining limits

No 26.2 comparison was required: these claims identify 1.16 behavior and specify no alleged later fix/cutover. The exact selected connection clauses and their movement consequences are accepted, but the task scope is these clauses, not full-pair movement coverage. The source candidate files were absent; the audit is standalone and does not promote or edit those drafts. No runtime parity claim is made.
