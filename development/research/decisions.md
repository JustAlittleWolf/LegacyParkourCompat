# Scope and invalid movement witnesses

The root README and AGENTS define current scope: historical client player movement on vanilla block states. Historical server physics, vehicle/non-player motion, rendering/input-device plumbing, state production, damage, and bed-entry eligibility do not become movement tasks. Direct player responses and predicates reading vanilla state remain eligible.

- **1.21.4/1.21.5 F09 input damping:** the newer controlled-camera path also applies 0.98F. The revised forward-only 1ULP witness cancels after square correction; its diagonal residual belongs to the separate normalization/square-movement operation. Neither claim supports a damping implementation.
- **1.19.4/1.20.1 trapdoor/wire:** the closed bottom trapdoor already has a FULL upward face in the older support test. The movement witness was withdrawn; later ACCEPT covers withdrawal only. Redstone/block-state production is separately excluded, and the open-trapdoor replacement is unproved.
- **1.21.4/1.21.5 scale refresh:** the older appliedScale poll follows dirty-attribute dispatch at tick tail. Both endpoints refresh before next movement; the stale-next-tick witness was withdrawn.
- **1.8.9/1.9.4 WORLD27 server boundary:** the configured +/-30M witness overlooks the effective 29,999,984 cap. Do not restore that witness or replay historical server physics; the eligible client correction operation is separate.
- **1.14.4/1.15.2 Toggle Crouch:** optional toggle input production is a modern feature; default hold-state edge loops match. This does not reject the independent near-ground edge predicate operation.
- **1.19.2/1.19.3 correction clamp:** the accepted response is to finite out-of-range absolute corrections from an external/custom server. Vanilla teleport rejects those coordinates; a codec is not a vanilla producer proof.
- **Honey 1.19.2/1.19.3:** acceptance establishes first stable changed release 1.19.3 for the command/local-gravity witness. Current V1_19 groups both endpoints. An equal old-key registration wins over a later key; inserting later keys filters older registrations. A corrected per-operation decision table and earlier-alias evidence are required before changing profile representation.

These constrain current work; they are not a campaign ledger. Preserve input-state movement predicates such as the pending sprint-timer operation even when they depend on input representation.
