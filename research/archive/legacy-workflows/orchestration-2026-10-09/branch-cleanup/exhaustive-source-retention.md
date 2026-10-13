# Source-ref retention and completed duplicate retirement

The proposed dormant-source retirement manifest was not executed. Automatic approval review rejected deletion of canonical unfinished assignment refs. All nineteen canonical partial source branches remain retained; each has a coordinator-owned ordered dispatch/resume action in the authoritative queue. Their unfinished status is unchanged. The six already archived partial intervals remain recoverable from their exact saved checkpoints; no new source branch is created until dispatch.

Only the completed superseded source base `feat/source-discovery-movement-source-1-21-4-1-21-5-resume` at `1d027c706dd22fd6df2fe14deaf3a02695d6094e` was separately archived and retired. It is an ancestor of the active clean continuation. Its historical checkout remains detached at the identical tip with all files retained; never resume its contaminated discovery lane.

The completed legacy `feat/movement-discovery-1-11` at `766ec5006a227d95983bfa91a9513a3ed2681baf` is an ancestor of main and was separately archived and retired. Its checkout remains detached at the identical tip. Tracked files were clean; the sole untracked `.gradle-home-movement-discovery/` cache directory remains present unchanged. No cache or directory deletion occurred.

Both full commit histories are protected under `refs/archive/exhaustive-closure-2026-10-09/<original-branch>`.
