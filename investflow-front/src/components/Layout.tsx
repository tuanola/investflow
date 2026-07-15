import { BarChart3, FileText, Home, PieChart, Upload, Wallet } from "lucide-react";
import { NavLink, Outlet } from "react-router-dom";

const navItems = [
  { to: "/", label: "Upload", icon: Upload },
  { to: "/imports", label: "Imports", icon: FileText },
  { to: "/overview", label: "Overview", icon: Home },
  { to: "/holdings", label: "Holdings", icon: Wallet },
  { to: "/transactions", label: "Transactions", icon: BarChart3 },
  { to: "/reports", label: "Reports", icon: PieChart },
];

export function Layout() {
  return (
    <div className="flex min-h-screen bg-slate-50 text-slate-950">
      <aside className="w-64 border-r border-slate-200 bg-slate-950 px-4 py-6 text-white">
        <div className="mb-8 px-2">
          <h1 className="text-2xl font-semibold tracking-tight">InvestFlow</h1>
          <p className="mt-1 text-sm text-slate-400">
            Portfolio analytics
          </p>
        </div>

        <nav className="flex flex-col gap-1">
          {navItems.map((item) => {
            const Icon = item.icon;

            return (
              <NavLink
                key={item.to}
                to={item.to}
                end={item.to === "/"}
                className={({ isActive }) =>
                  [
                    "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
                    isActive
                      ? "bg-white text-slate-950"
                      : "text-slate-300 hover:bg-slate-800 hover:text-white",
                  ].join(" ")
                }
              >
                <Icon className="h-4 w-4" />
                {item.label}
              </NavLink>
            );
          })}
        </nav>
      </aside>

      <main className="flex-1 px-8 py-6">
        <div className="mx-auto max-w-7xl">
          <Outlet />
        </div>
      </main>
    </div>
  );
}