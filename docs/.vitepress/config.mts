import { defineConfig } from "vitepress";
import { withMermaid } from "vitepress-plugin-mermaid";

// https://vitepress.dev/reference/site-config
const vitepressConfig = defineConfig({
  base: "/foerdermittel/", // needs to be changed if delivered via sub path (e.g. "/docs/" for example.com/docs)
  title: "Fördermittel Documentation",
  description: "Documentation template from the RefArch Templates",
  head: [
    [
      "link",
      {
        rel: "icon",
        href: `https://assets.muenchen.de/logos/lhm/icon-lhm-muenchen-32.png`,
      },
    ],
  ],
  lastUpdated: true,
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: "Home", link: "/" },
      {
        text: "Docs",
        items: [{ text: "Architecture", link: "/arc42" }],
      },
    ],
    sidebar: [
      {
        text: "Architecture",
        link: "/arc42",
        items: [
          {
            text: "01 Introduction and Goals",
            link: "/arc42/01_introduction_and_goals",
          },
          {
            text: "02 Architecture Constraints",
            link: "/arc42/02_architecture_constraints",
          },
          { text: "03 Context and Scope", link: "/arc42/03_context_and_scope" },
          { text: "04 Solution Strategy", link: "/arc42/04_solution_strategy" },
          {
            text: "05 Building Block View",
            link: "/arc42/05_building_block_view",
          },
          { text: "06 Runtime View", link: "/arc42/06_runtime_view" },
          { text: "07 Deployment View", link: "/arc42/07_deployment_view" },
          { text: "08 Cross-cutting Concepts", link: "/arc42/08_concepts" },
          {
            text: "09 Architecture Decisions",
            link: "/arc42/09_architecture_decisions",
          },
          {
            text: "10 Quality Requirements",
            link: "/arc42/10_quality_requirements",
          },
          {
            text: "11 Risks and Technical Debts",
            link: "/arc42/11_technical_risks",
          },
          { text: "12 Glossary", link: "/arc42/12_glossary" },
        ],
      },
    ],
    socialLinks: [
      { icon: "github", link: "https://github.com/it-at-m/refarch-templates" },
    ],
    editLink: {
      pattern:
        "https://github.com/it-at-m/refarch-templates/blob/main/docs/:path",
      text: "View this page on GitHub",
    },
    footer: {
      message: `<a href="https://opensource.muenchen.de/impress.html">Impress and Contact</a>`,
    },
    outline: {
      level: "deep",
    },
    search: {
      provider: "local",
    },
  },
  markdown: {
    image: {
      lazyLoading: true,
    },
  },
});

export default withMermaid(vitepressConfig);
