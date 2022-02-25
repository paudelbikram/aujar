package com.techpasya.aujar.model;

import java.util.Objects;

public class SearchBoundary {

    private String packagePath;
    private String[] packageBreakDown;

    public SearchBoundary(String packagePath) {
        Objects.requireNonNull(packagePath, "Boundary Package Path is required");
        this.packagePath = packagePath;
        packageBreakDown = packagePath.split("\\.");
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packageName) {
        this.packagePath = packagePath;
    }

    public boolean isFromThePackage(final String packageName) {
        if (packageName == null || packageName.trim().isEmpty()) {
            return false;
        }
        final String[] providedPackageInSplit = packageName.split("\\.");
        if (packageBreakDown.length > providedPackageInSplit.length) {
            return false;
        }

        for (int i = 0; i < packageBreakDown.length; i++) {
            if (!providedPackageInSplit[i].equalsIgnoreCase(packageBreakDown[i])) {
                return false;
            }
        }
        return true;
    }

}
