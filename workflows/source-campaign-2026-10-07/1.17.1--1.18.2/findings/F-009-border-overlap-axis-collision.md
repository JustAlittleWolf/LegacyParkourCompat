# F-009: Border overlap changes one-axis player collision

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-ENTITY-COLLISION,T-EDGE-GATE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A `Entity#collide` gets the world-border shape and omits it when the current bounding box deflated by `1.0E-7` intersects that outside-border shape (Entity.java lines 749-758; SHA-256 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`). B `Entity#collideBoundingBox` adds the border shape when `WorldBorder#isInsideCloseToBorder(entity, box.expandTowards(requestedDelta))` passes, then appends block collisions (Entity.java lines 803-816; SHA-256 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; WorldBorder.java lines 76-79; SHA-256 `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`). Both versions construct the same outside-of-border shape from the floored minimum and ceiled maximum coordinates (`WorldBorder.java`; A SHA-256 `b848dd0dc6043d6c9012726844bea53cf4558c0135a08fa3be55960b0e557df9`).

## Source-level difference

For a player already overlapping the east outside-border region, with no block or entity obstacles, request a one-axis move north across the north boundary. A sees the current-box overlap and omits the entire border shape; its scalar collision path has no border candidate and returns the requested movement. B's near-border guard passes for the swept box, includes the outside-border shape, and clips the northward movement at the north boundary. The VoxelShape collision kernel and axis arithmetic match across versions. This changes the returned movement vector and resulting player position.

A reachable setup is a world-border size or center change that places the border across a stationary player; `WorldBorder#setSize` and `#setCenter` replace/update the extent without relocating entities (A lines 97-125; B lines 109-136). The collision precondition then persists into the player's next ordinary movement.

## Reachability and dependencies

The path is the normal non-zero `Entity#move` collision call used by Player. The scalar branch applies when exactly one requested component is non-zero. A `Shapes.collide` scans block states and then applies its entity/border stream; with no blocks/entities and the border stream omitted, the north delta remains unchanged. B materializes entity shapes, conditionally appends the border, and then clips against that shape. No tests or gameplay were run.

D-BORDER-MOVE-PATH source coverage is complete for the paired auto-jump, crouch edge-gate, and final solver paths (F-004, F-008, F-009). The generic query order difference is independently recorded in F-003.

## Handoff

Source discovery only; implementation deferred. Candidate is not submitted for independent review.