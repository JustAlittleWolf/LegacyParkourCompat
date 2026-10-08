# F-002: Fall-flying coefficient changes from float-table to double cosine

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-ELYTRA
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A LivingEntity#travel 2074-2090, SHA 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f: float Mth.cos and cast. B 2080-2099, SHA db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782: double Math.cos retained.

## Source-level difference

The coefficient used by the same lift/pull equations changes from float table/cast to double cosine/arithmetic.

## Reachability and dependencies

LocalPlayer Elytra transition -> Player#travel -> LivingEntity#travel; equipment eligibility closure open.

## Consequence and uncertainty

Different computation is proven; velocity consequence is inferred and unmeasured.

## Handoff

Source discovery only; implementation deferred.
