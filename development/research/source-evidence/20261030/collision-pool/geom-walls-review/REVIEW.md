# Independent review: wall single-arm and three-arm collision drafts

Verdict: accept the two bounded geometry and conditional collision-query claims as one `WallBlock` collision-shape operation family. Do not infer a natural input trajectory or runtime parity.

Reviewed immutable inputs:

- `wall-single-arm-gaps.md`: SHA-256 `E051F4920FCB35E4745663601AA647572F9CA0D3D5BB0E2EC581AB1C857D0AC8`.
- `wall-three-arm-corner-gaps.md`: SHA-256 `432DC582A6D8286318D5ECA156E7D26C4968BC4AB7EB9AB03ECEDE5F41DF4531`.
- Review closure: `.native-source/20261030/collision-pool/geom-walls/review-closure.md`, SHA-256 `8C61BA9A9410AEA49E39605B2B089421704030B93125A7A5178133139CE3F295`.

I independently checked the canonical version provenance, ready records and source manifest references for 1.12.2, 1.13, 1.13.1 and 1.13.2. The `WallBlock.java`, `PaneBlock.java`, and `FenceGateBlock.java` hashes in the closure match all four canonical trees. The 1.13–1.13.2 geometry files are byte-identical; the 1.12.2 `WallBlock` is distinct. The 1.13 and 1.13.1/.2 registration files differ overall, but each retains both cobblestone wall registrations and the required wall fields.

The mask math is correct: 1.12.2 horizontal direction IDs give north-only mask 4 and north/east/south mask 13. Old collision uses one mask-selected box extended to 1.5 high. New `PaneBlock` shapes union the central post with arms, with a 6/16 exposed arm and 8/16 central post; `UP` determines whether the tall wall collision table or fallback pane shape is returned. The stated air-above conditions produce `UP=true` for both masks. An open fence gate facing across its queried wall face supplies the required middle-pole face while remaining collision-empty, so it is a valid vanilla neighbor for both connection calculations.

The conditional witnesses use player-sized boxes and an airborne movement request to show different collision-query clipping at old and new wall components. They do not claim an ordinary input tick produces the exact box and request. One prose constraint is material: the old box's west-side corner sliver is open only outside the center post's z span; the post fills the center strip. The closure identifies this qualification, along with the limit on the single-arm width comparison.

No source gate remains for the two inspected geometry masks and the 1.12.2→1.13 endpoint difference. The 1.13.1/.2 aliases are confirmed. Natural movement reachability, intermediate boundaries outside these exact releases, and runtime parity remain open.
