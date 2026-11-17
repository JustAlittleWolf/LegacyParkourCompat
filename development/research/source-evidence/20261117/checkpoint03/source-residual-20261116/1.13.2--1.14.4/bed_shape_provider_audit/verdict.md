# Bed shape provider source audit

**Verdict: ACCEPT** for the bounded bed shape provider claim in `../bed_shape_provider/report.md`.

## Immutable inputs verified

The audited report SHA-256 is `509950547603b487a25cbc83435367bc2a2cde743a9442909e8d1dde5f17d11a`.

Both ready markers identify status `ready`, mapping `ornithe-feather`, and the source-manifest digests below. The exact on-disk flat manifest SHA-256 values match the markers:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`

Every cited Java file hash in the report matches both its manifest entry and the local source bytes:

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/BedBlock.java` | `a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18` | `aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` | `498a72cdd4bd035cd731d2c49fce401bef439d6f92f498501577d9cfc59ab487` |
| `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909` |

## Claim audit

- The geometry comparison is accurate. In 1.13.2, `BedBlock.SHAPE` is the full local footprint from y=0 through 9/16 and `getShape` returns it for every state. In 1.14.4, the provider unions the y=3/16..9/16 full top with two 3/16-square corner feet down to y=0. Its `FACING`/`PART` selection orients those feet on the north/south/west/east edge: `HEAD` uses the facing direction and `FOOT` its opposite. `OCCUPIED`, world, position, and collision context do not affect the result. `Block.block` divides the six coordinates by 16 in both releases.
- The stated state path is accurate: both versions define `FACING`, `PART`, and `OCCUPIED`, default the bed to `FOOT` and unoccupied, set placement facing from `ctx.getFacing()`, and write the neighboring head half at `pos.offset(FACING)`. These state fields therefore feed the 1.14 provider for ordinary placed beds.
- Ordinary collision routing is accurate. In 1.13.2, `Entity.move` queries `world.getCollisions` before clipping requested movement; the world/view collision implementation enumerates block positions, calls `BlockState.getCollisionShape`, offsets the resulting shapes, and filters them for overlap. In 1.14.4, `Entity.move` enters its movement resolver, which queries `WorldView.getBlockCollisions` on the multidirectional route and queries each candidate state through its context-taking collision method on the axis route. The default `Block.getCollisionShape` delegates through `BlockState.getSHape`, which uses an empty context; BedBlock's provider ignores context in any event. `PlayerEntity` inherits from `LivingEntity`, whose travel invokes the inherited entity movement method; no player-specific movement override interrupts this route. Thus player movement can consume the bed geometry in both endpoints.
- The report correctly bounds its consequence: it establishes a collision-input difference, not a concrete standard-pose player trajectory that must diverge in every bed encounter.

## Limits

This accepts only the bounded bed provider/state/collision reachability report. It does not establish complete pair coverage or pair freeze, close other dependencies, prove a concrete trajectory or runtime parity, or identify the exact intermediate release where the geometry changed. No product, wiki, Git history, build, test, or game material was consulted.
