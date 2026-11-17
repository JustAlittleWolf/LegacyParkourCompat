# Independent source audit: 1.15.1 → 1.15.2 citation correction r2

Verdict: **ACCEPT — corrected hash binding only**

Bound report: `build/source-mid-patches-20261117/1.15.1--1.15.2/ordinary_player/source-report-r2.md` — SHA-256 `322E867BEA50AF506E4438F4809D3C893B5E2897B656F02C0855D41A43274D2B`.

## Correction checked

The prior independent audit (`build/source-mid-patches-20261117/1.15.1--1.15.2/ordinary_player/audit/verdict.md`, SHA-256 `16E185BD8A90B954C2B4D0372792BEA229173B04EF74E41545DEA61EAE293B49`) requested only a correction to the unchanged `MobEffects.java` citation. It identified the manifest-bound SHA-256 as `472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412`.

I compared the r1 report with r2. Exactly one report line differs: the `MobEffects.java` citation now contains that corrected value; all other report lines are unchanged. I independently rechecked the r2 file SHA-256 against the bound value above. This accepts the requested correction and reuses the prior audit's unchanged source-identity and technical checks under the workflow's immutable-verdict reuse rule. No full-tree rehash or new source comparison was performed.

The report remains **PARTIAL**. This verdict does not close resource/provider dependencies or remaining pair coverage, establish runtime behavior or reachability, or freeze the pair.
