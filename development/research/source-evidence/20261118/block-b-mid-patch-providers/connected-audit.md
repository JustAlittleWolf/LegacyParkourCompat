# Independent source verdict: connected stationary collision slice

## Verdict

- **ACCEPT — bounded no-difference conclusion.** For the assigned stationary, fixed-state cobblestone wall, oak/nether-brick fence, iron-bars/glass-pane collision roster, the endpoint sources support the producer's conclusion that the returned outline and collision shapes, state-keyed selection, named registrations, and ordinary collision scan/admission/clipping have no 1.16.1-to-1.16.2 movement difference. This is not a full-pair or runtime-parity verdict.
- **CORRECT — source line ranges.** The producer artifact has two imprecise start lines: 1.16.1 `WallBlock.makeShapes` begins at line 68 (not 58); `CrossCollisionBlock.makeShapes` begins at line 46 (not 44). The stated ending lines and the dispatched getter ranges are correct.
- **OPEN — evidence completeness only.** The artifact does not explicitly document the assigned `BlockBehaviour` pin. I independently compared the relevant default collision method, `BlockState` forwarding/cached-shape path, and collision cache initialization; those bodies match at both endpoints. Add that evidence to close the record. No source behavior difference remains open within that pin.

## Identity and artifact check

Producer artifact `connected-collision-1.16.1--1.16.2.md` SHA-256: `fabb721a6a64ae2816a5751167fa9525dab02a07ddda48805b819bc5e3896d5a` (matches the assigned immutable identity).

Canonical sources are `decompiled_minecraft/{1.16.1,1.16.2}/mojmap`. Their source-manifest file hashes match both the ready markers and supplied identities: 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. Provenance-file hashes match: 1.16.1 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; 1.16.2 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`.

## Compared evidence

All paths are relative to `decompiled_minecraft/<version>/mojmap/`. SHA-256 values below are for the complete source files and match their respective `mojmap.sources.sha256` entries.

- `net/minecraft/world/level/block/WallBlock.java`: 1.16.1 `ce153c55791f44eb92abc5f2febfbf023a3e03e63252d94201e3ab843d18b5d4`; 1.16.2 `aa770704573ee65ca0d0b102bc55e3ece81f7b25aef452bb5c89ec0350a23442`. Both define the same collision and outline shape arguments at lines 56–57; map construction is lines 68–113, state-keyed `getShape` is 116–118 and `getCollisionShape` is 121–123. The hash difference includes source changes outside these operations; the shape-generation and dispatch operations match.
- `net/minecraft/world/level/block/CrossCollisionBlock.java`: both `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4`. Shape generation is lines 46–82; `getShape`/`getCollisionShape` are 90–97; horizontal state-to-mask cache is 103–123.
- `net/minecraft/world/level/block/FenceBlock.java`: 1.16.1 `1337151f78048fcef2356d128599dec6e688a01b10e21e8a409f767796f93588`; 1.16.2 `ded56e297e8711e382d485aed4cb34ab0e827bec9a033b1f6c16947592fa15f2`. Constructor and parameters are 30–36 at both endpoints; the inherited collision shape comes from the identical `CrossCollisionBlock` implementation.
- `net/minecraft/world/level/block/IronBarsBlock.java`: 1.16.1 `1e1c1e27005e4367152b46df5219aeed177e634bb03abbc1b04fd4e329c9cb69`; 1.16.2 `e84392b278ceeaf02abb0c72c74e1011629d7c0add57f6bf0f6ec5b6006feaab`. Constructor/state default are 19–25 at both endpoints; the inherited collision shape is unchanged.
- `net/minecraft/world/level/block/Blocks.java`: 1.16.1 `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`; 1.16.2 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`. Named registrations are `OAK_FENCE` 712–714 / 718–720; `IRON_BARS` 899–918 / 906–925; `GLASS_PANE` 919–921 / 926–928; `NETHER_BRICK_FENCE` 970–978 / 977–985; `COBBLESTONE_WALL` 1085 / 1092. They retain their class, registry name, and collision-affecting property construction. These lines establish only the assigned named registration roster.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: 1.16.1 `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f`; 1.16.2 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`. The default `getCollisionShape` at 270–277 / 271–278, `BlockState` getters at 516–529 / 517–530, and cache's empty-context collision-shape construction and bounds classification at 744–746 / 752–754 match.
- `net/minecraft/world/level/CollisionSpliterator.java`: both `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`. Ordinary scan and admission/clipping path is lines 56–89, identical at both endpoints.

This verdict is bounded to the assigned pins and stationary ordinary collision slice. It excludes neighbor-state generation, world-border handling, AutoJump, pose/dimensions, other block families, and full-pair coverage.
