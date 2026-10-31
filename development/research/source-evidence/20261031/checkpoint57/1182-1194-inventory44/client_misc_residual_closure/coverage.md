# Residual client miscellany namespace closure

**Scope:** changed Mojmap paths classified client-other under client resources, tutorial, search, telemetry, animation, color, client server/main, and miscellaneous client top-level classes across 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4. The selected path roster has **138 unique paths and 206 changed edge/path rows**. Per-path roles and counts are in path-dispositions.tsv (SHA-256 F6B66A57CFBE3C38380DCE4D3ECA183321AC05D1798E475C3CEEF74C40ED4CA3). The six source roots and source manifest identities are listed in whole-source-path-inventory.md. The full changed Java path/hash table is changed-paths.tsv (SHA-256 FC00141CB9C9ED40AA24B59429DFBB4DB7AEDE3C9657AF82F55E429C59511606); role table SHA-256 8A3F647F94F5B9A556537C0592A32DB9FAB34AAB87F35E71D85F16E2BFE041EF.

Previously assigned lanes already cover input-device plumbing/options, LocalPlayer tick and client-level/network, plus GUI/renderer/model/particle/sound presentation. Those classes are marked as previously covered or presentation-only in the table and were not re-audited here. The current lane concentrated on remaining resource, telemetry, search, tutorial, animation, color, server/main, and miscellaneous client roles.

## Movement-route result

No new ordinary LocalPlayer movement writer, direct movement predicate or callback was found in this residual client miscellany. Specific player-touching changed members were terminally disposed as read-only/UI:

- MovementTutorialStepInstance and tutorial callbacks read raw input/progress to decide prompt timing and content; they update tutorial progress/toasts only. They do not mutate input or player movement state.
- ElytraOnPlayerSoundInstance.tick reads local-player position/removal/fall-flying to place and stop a sound instance. It does not write player position, velocity, flags, or input.
- Camera reads player position/pose for the view transform and affects rendering only.

Other selected paths are resource/model/locale/sound metadata, telemetry/event logging, search-tree/UI, animation/color rendering, session/startup/configuration, or integrated-server/LAN code. Integrated-server/state production is excluded. The classes explicitly marked covered by existing input/tick lane are not duplicated in this check.

Disposition summary:
- block/item tint rendering only: 3 paths / 6 edge rows
- client startup/configuration only: 2 paths / 4 edge rows
- covered by existing input/tick lane; not re-audited: 6 paths / 17 edge rows
- integrated/LAN server path; server/state production excluded: 3 paths / 6 edge rows
- option UI; setting mutation/read excluded from raw input comparison: 6 paths / 8 edge rows
- presentation capture/camera only: 2 paths / 6 edge rows
- render-animation only: 10 paths / 13 edge rows
- render-model resources only: 13 paths / 16 edge rows
- render/status preference only: 7 paths / 14 edge rows
- resource/asset/locale/texture metadata only: 22 paths / 37 edge rows
- sound instance/event only: 15 paths / 20 edge rows
- telemetry/event logging only: 18 paths / 19 edge rows
- tutorial input/progress/UI only; no movement write: 7 paths / 9 edge rows
- UI/search registry only: 11 paths / 11 edge rows
- UI/session/recipe/text/display; no movement writer found: 13 paths / 20 edge rows

The table is a path-role closure with targeted member checks for movement-touching classes; it does not claim every unrelated method in each class received a full semantic diff. This is not an independent full-pair member audit or pair-freeze claim; changed-member residuals remain elsewhere in the pair.
