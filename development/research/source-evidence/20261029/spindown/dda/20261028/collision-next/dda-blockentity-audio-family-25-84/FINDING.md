# Source closure: note block and jukebox provider dependencies

Scope: exact 1.8.9 Ornithe Feather and 26.2 unobfuscated source for legacy registry IDs 25 and 84. This report covers block identity/state, collision and ray-bound providers, and whether their block entities or neighbors are queried by those providers. It does not establish DDA visitation, hit ordering, first-hit equivalence, or gameplay reachability.

## Identity and state projection

- 1.8.9 net/minecraft/block/Block.java:878 registers numeric ID 25 as noteblock (NoteBlock); line 1015 registers numeric ID 84 as jukebox (JukeboxBlock).
- Current net/minecraft/references/BlockItemIds.java:324,574 identifies jukebox and note_block. net/minecraft/world/level/block/Blocks.java:691-695,2016-2020 registers those keys as NoteBlock and JukeboxBlock. These names support the bounded identity correspondence; this review did not trace serialized numeric-ID datafix execution.
- Old note block state has no subclass metadata projection: Block.getStateFromMetadata returns the default state (Block.java:182-186), and NoteBlock does not override it. Its note (0-24) and powered flag live in NoteBlockBlockEntity (NoteBlockBlockEntity.java:10-32), not in block metadata.
- Old jukebox overrides metadata: JukeboxBlock.java:116-123 projects metadata greater than zero to HAS_RECORD=true, and state back to 1/0. The record itself is in its nested JukeboxBlockEntity (JukeboxBlock.java:130 onward).
- Current note block is a plain Block and declares INSTRUMENT, POWERED, and NOTE (NoteBlock.java:37-51,182-184), with no block-entity factory or registered note-block entity type in the examined source. Current jukebox remains a block entity (JukeboxBlock.java:31,91; BlockEntityTypes.java:30) and declares HAS_RECORD (JukeboxBlock.java:34,116-118). Its entity holds item/song playback data (JukeboxBlockEntity.java:22-30,75-94). Old metadata 0/1 aligns with current HAS_RECORD; note block's old entity note data has no direct current block-entity counterpart.

## Historical provider behavior

Both old classes extend BlockWithBlockEntity, whose constructor sets only the hasBlockEntity flag after the Block constructor initializes unit bounds (BlockWithBlockEntity.java:13-22; Block.java:198-205). Neither block class overrides getCollisionShape, rayTrace, or updateShape. The inherited collision provider constructs its box from the block's current min/max fields (Block.java:339-350), while inherited ray tracing invokes updateShape before clipping (Block.java:456-458). The base updateShape is empty (Block.java:596); base ray eligibility returns true (Block.java:356-362). Therefore, the source-defined collision box and ray box for these block instances are the unit cube, with no per-query bound writer.

The old note block entity's note/powered fields are consumed by note tuning/playback and redstone-neighbor event logic (NoteBlock.java:20-68; NoteBlockBlockEntity.java:12-53). Old jukebox entity contents are consumed by record insertion/removal and comparator output (JukeboxBlock.java:37-108,130-153). Those interaction/event paths do not participate in collision or ray-bound provider methods. Old BlockWithBlockEntity also makes the full-cube classification false through the hasBlockEntity condition (Block.java:668-673), even though the direct default collision box is the unit cube. An adapter must not equate that classification with an empty or partial collision shape.

## Current provider behavior and context

Neither current class overrides getShape or getCollisionShape. BlockBehaviour supplies Shapes.block() as the default outline and uses the state shape as collision whenever hasCollision is true (BlockBehaviour.java:323-328). BlockBehaviour.Properties defaults hasCollision to true and noCollision flips it off (BlockBehaviour.java:985,1094); the two registrations do not call noCollision (Blocks.java:691-695,2016-2020), so both use full-block collision and default full-block outline. Jukebox entity reads in JukeboxBlock serve record/playback/comparator behavior, not shape calculation.

Current note block updateShape reads the block above/below to choose its INSTRUMENT value (NoteBlock.java:54-84); this is neighbor-dependent gameplay state, not a neighbor-dependent shape. Its separate getCustomSoundId can inspect a skull block entity above (NoteBlock.java:178), but only for playback. Neither dependency enters the shape/collision override path, which is inherited and block-shaped. Current jukebox HAS_RECORD and its block entity data affect interaction/redstone/playback (JukeboxBlock.java:47-112), not shape or collision.

## Adapter dependency

For this bounded pair, exact old provider behavior does not require reconstructing note pitch, powered state, record contents, or neighbor state to compute collision/ray boxes; both provider boxes remain unit bounds. A faithful broader old-state adapter still needs to preserve the legacy block identity and the distinction that these block-entity blocks fail the old full-cube classification despite their unit collision boxes. Current note block's modern state properties and neighbor-selected instrument must not be mistaken for historical ray or collision inputs. Jukebox's block entity is current and historical, but its contents are likewise not shape-provider inputs.

Source hashes and canonical-manifest comparisons are recorded in source-hashes.json.
