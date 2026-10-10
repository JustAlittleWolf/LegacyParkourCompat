# Continuation correction event: pane-provider slice 40

## Corrected next-registration statement

This append-only event corrects the final sentence of the independent review report `reviews/independent-review-pane-neighbor-providers-fortieth-2026-10-10.md`, committed at `7042a4cb3c50b865d86bb95d09c55934b638549f`. The preserved report Git blob is `d5fd5780a6fa868c1a095f41e3b85835a550061e`; its raw SHA-256 is `04233e8b5636234b089e556a46518f593e994958ef20022dd0f562a7827c4592`.

The report says: “The next registrations are IDs 83–84; ID 85 has no registration in either checked source.” The ID 85 absence assertion is incorrect. Exact canonical `Block.java` source registers `fence` at numeric ID 85 in both 1.8.9 (lines 1016–1020) and 1.9.4 (lines 917–925). Slice 41's frozen memo and independent source review classify Fence as one of its three providers, alongside Reeds (ID 83) and Jukebox (ID 84). The next lowest shared registration after that slice is Pumpkin (ID 86), present in both registries.

The relevant source files are bound to the canonical ready manifests:

| Version | `Block.java` SHA-256 | Source-manifest SHA-256 |
|---|---|---|
| 1.8.9 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` |
| 1.9.4 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |

## Verdict handling

**Correction: ACCEPTED, next-registration metadata only.** Slice 40's ACCEPT verdict and its bounded findings for IDs 80–82 are unchanged. The original review report and author memo remain byte-for-byte preserved. This event corrects only the next-registration statement; it does not certify census closure or change any slice 40 pane-mask result.

The slice 41 evidence is independently recorded in `reviews/independent-review-pane-neighbor-providers-forty-first-2026-10-10.md`. Full provider closure, all world-reachable pane masks, and original derived-JAR equivalence remain open.
