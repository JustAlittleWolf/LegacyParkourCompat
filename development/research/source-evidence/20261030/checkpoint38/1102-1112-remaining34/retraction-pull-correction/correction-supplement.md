# Retraction follow-up-pull witness corrections

This supplement preserves the immutable witness at SHA-256 `8D994F9CF76187EE0CFBE7D6F9940071EB2982FE3E9AF3219502C67408FB58FC` and binds its independent source audit at SHA-256 `D62C8CFDA736B91B0CC9084EF2BA315D2447B36838A7F47B8DDFA0ADFD933BE2`.

The source-retraction witness remains supported for its stipulated response-entry AABB and explicit tick/budget conditions. Correct the old 1.10.2 moved-state description: the scheduled direction event supplies `data=5`, so `getStateFromMetadata` sets `EXTENDED=false`. The old moved state is an unextended base whose full-block outline at offsets `0.5` and `1` yields union X `[0.5,2]`, rather than `[0.5,1.75]`. The westward request remains `1.61 - 0.5 + 0.01 = 1.12`; with the stated clear westward route it remains unclipped and produces the same endpoint.

For an isolated sticky-piston response, add the condition that block `(2,0,0)` is air, or use a nonsticky piston, to avoid the optional second pulled block. Keep the 1.11.2 PISTON accumulator condition explicit: no earlier same-tick X piston displacement. The exact prior control/velocity history for the stipulated upward `+0.05` request remains untraced, so this is a paired source-level response construction, not a complete ordinary-input trajectory or runtime parity claim.
