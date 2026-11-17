# Independent audit verdict: wall piston faces

Verdict: **ACCEPT**

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_piston_faces/report.md` (SHA-256 `8f9bcb667c10006430b3bb0b66cb9235e92c00a7d691613d0c6c1632fac425c4`).

## Evidence identity

The ready markers identify 1.13.2 and 1.14.4 Ornithe Feather Gen2 build 2. Their source manifest digests match the assigned values: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. The report's paired SHA-256 values match the local source files for both `Block.java`, both `PistonBaseBlock.java`, both `PistonHeadBlock.java`, and the old `Block.java` / target `Blocks.java` registration sources.

## Independently checked claims

- Both registries define `piston`, `sticky_piston`, and `piston_head`. The two base keys instantiate `PistonBaseBlock` with differing sticky constructor flags; the head key instantiates `PistonHeadBlock`. The corresponding source registrations match the report's cited lines.
- Both endpoints define base states with `FACING × EXTENDED` and head states with `FACING × TYPE × SHORT`. Extension builds the head with facing/type; each endpoint's `MovingBlockEntity` also constructs a head state with `SHORT`. The claimed property coverage is supported.
- In 1.13.2, `Block.isExceptionForAttachment` explicitly includes all three piston blocks. The old wall generic neighbor predicate first rejects attachment exceptions, so its result is false for all their states and directions regardless of the directional face shape. The cited old face overrides are accurately described: an extended base reports `SOLID` only on the face opposite its facing; a head reports `SOLID` only on its facing face.
- In 1.14.4, none of the three keys is in `Block.isExceptionForAttachment`. The supplied target `walls.json` payload has the cited SHA-256 and contains only 14 wall IDs, with none of these keys. The generic wall arm therefore uses `Block.isFaceSolid`, which checks the direction-projected collision shape for a full face and excludes leaves. The three piston blocks are not leaves.
- Target unextended base collision shape is a full cube. For an extended base, the axis-aligned slab shapes leave only the face opposite `FACING` full. The target head's full 4/16 plate covers only its `FACING` face; its narrower arm does not make another face full. `TYPE` does not affect geometry, and either `SHORT` arm variant leaves the plate unchanged. This verifies the directional table for every facing and the claimed short/type variants.

## Corrections

None. No product, wiki, backlog, history, test, build, game, network, or source-generation inputs were used. This verdict accepts only the assigned piston-family source claims and does not establish pair coverage or freeze.
